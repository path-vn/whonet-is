import './home.scss';

import React, { useEffect, useRef, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Storage, Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Alert, Col, Row } from 'reactstrap';
import { JsonEditor as Editor } from 'jsoneditor-react';
import ace from 'brace';
import { interpretationEntity, interpretationFile } from '../../entities/execute/execute.reducer';
import { IRootState } from 'app/shared/reducers';
import { empty } from 'app/shared/util/entity-utils';
import { saveAs } from 'file-saver';

// export type IHomeProp = StateProps;

export interface IHomeProp extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Home = (props: IHomeProp) => {
  const { account } = props;

  const [history, setHistory] = useState(
    empty(Storage.local.get('_interpretation_history')) ? [] : Storage.local.get('_interpretation_history')
  );
  const [json, setJson] = useState(
    history.length === 0
      ? [
          {
            orgCode: 'eco',
            dataFields: { BETA_LACT: '-' },
            test: [{ rawValue: '4', whonet5Code: 'AMK_ND30' }],
          },
        ]
      : history[0]
  );
  const [jsonResult, setJsonResult] = useState({});
  const [key, setKey] = useState(0);
  const ref = useRef();
  const expand = val => {
    val.expandAll();
  };

  useEffect(() => {
    if (empty(props.fileDownload)) {
      return;
    }
    saveAs(props.fileDownload, props.fileDownloadName);
  }, props.fileDownload);

  useEffect(() => {
    if (ref.current !== null && typeof ref.current !== 'undefined') {
      expand(ref.current);
    }
  }, [key]);
  useEffect(() => {
    if (history.length > 0) {
      Storage.local.set('_interpretation_history', history);
    }
  }, [history]);

  const jsonEditorGetvalue = refE => {
    if (empty(refE) || empty(refE.current)) {
      alert('Unknown error');
    }
    return refE.current.jsonEditor.get();
  };

  const interpretationHandle = () => {
    const value = jsonEditorGetvalue(ref);
    if (!history.map(j => JSON.stringify(j)).includes(JSON.stringify(value))) {
      const newHistory = [value];
      newHistory.push(...(history.length > 50 ? history.slice(0, 50) : history));
      setHistory(newHistory);
    }
    setJson(value);
    props.interpretationEntity(value);
  };

  const clearHistoryHandle = () => {
    setHistory([]);
  };

  useEffect(() => {
    setJsonResult(props.result == null ? {} : props.result);
    setKey(Math.random());
  }, [props.result]);

  useEffect(() => {
    setKey(Math.random());
  }, [json, jsonResult]);

  const fileUpload = evt => {
    props.interpretationFile(evt);
  };

  return (
    <>
      <Row>
        <Col md="5" className="pad">
          <Editor ref={ref} value={json} key={`i-${key}`} ace={ace} theme="ace/theme/github" />
          <br />
          <button style={{ float: 'right' }} className={'btn btn-info'} onClick={interpretationHandle}>
            Interpretation
          </button>

          <div>
            <label htmlFor="file">Choose a file</label>
            <input className="inputfile" type="file" id="file" onChange={fileUpload} style={{ float: 'right' }} />
          </div>
        </Col>
        <Col md="7" className="pad">
          {account && account.login ? (
            <div>
              <Editor readOnly={true} key={`k-${key}`} value={jsonResult} ace={ace} theme="ace/theme/gob" />
            </div>
          ) : (
            <div>
              <h2>
                <Translate contentKey="home.title">Welcome to WHONET interpretation service!</Translate>
              </h2>
              <Alert color="warning">
                <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

                <Link to="/login" className="alert-link">
                  <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
                </Link>
                {/*<Translate contentKey="global.messages.info.authenticated.suffix">*/}
                {/*  , you can try the default accounts:*/}
                {/*  <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)*/}
                {/*  <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).*/}
                {/*</Translate>*/}
              </Alert>

              {/*<Alert color="warning">*/}
              {/*  <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;*/}
              {/*  <Link to="/account/register" className="alert-link">*/}
              {/*    <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>*/}
              {/*  </Link>*/}
              {/*</Alert>*/}
            </div>
          )}
        </Col>
      </Row>
      <Row>
        {account && account.login && (
          <>
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">OrgCode</th>
                  <th scope="col">Test</th>
                  <th scope="col">RawValue</th>
                </tr>
              </thead>
              <tbody>
                {history.map((h, i) => {
                  return (
                    <tr
                      key={`key-${i}`}
                      onClick={() => {
                        setJson(h);
                        setJsonResult({});
                      }}
                    >
                      <th scope="row">{i + 1}</th>
                      <td>{h.map(x => x.orgCode).join(',')}</td>
                      <td>{h.map(x => x.test.map(xi => xi.whonet5Code)).join(',')}</td>
                      <td>{h.map(x => x.test.map(xi => xi.rawValue)).join(',')}</td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
            <button style={{ float: 'right' }} className={'btn btn-primary'} onClick={clearHistoryHandle}>
              Clear
            </button>
          </>
        )}
      </Row>
    </>
  );
};

const mapStateToProps = ({ execute, authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
  result: execute.result,
  fileDownload: execute.fileDownload,
  fileDownloadName: execute.fileDownloadName,
});

const mapDispatchToProps = {
  interpretationEntity,
  interpretationFile,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Home);
