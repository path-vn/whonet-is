import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Alert, Col, Row } from 'reactstrap';
import { JsonEditor as Editor } from 'jsoneditor-react';
import ace from 'brace';
import { interpretationEntity } from '../../entities/execute/execute.reducer';
import { IRootState } from 'app/shared/reducers';

// export type IHomeProp = StateProps;

export interface IHomeProp extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Home = (props: IHomeProp) => {
  const { account } = props;
  const [json, setJson] = useState([
    {
      orgCode: 'ppm',
      dataFields: { BETA_LACT: '-' },
      test: [{ rawValue: '4', whonet5Code: 'AMC_NE', result: null }],
    },
  ]);
  const [jsonResult, setJsonResult] = useState({});
  const [key, setKey] = useState(0);
  const interpretationHandle = () => {
    props.interpretationEntity(json);
  };
  useEffect(() => {
    setJsonResult(props.result == null ? {} : props.result);
    setKey(Math.random());
  }, [props.result]);

  return (
    <Row>
      <Col md="5" className="pad">
        <Editor value={json} onChange={setJson} ace={ace} theme="ace/theme/github" />
        <br />
        <button className={'btn btn-primary'} onClick={interpretationHandle}>
          Interpretation
        </button>
      </Col>
      <Col md="7">
        {account && account.login ? (
          <div>
            <Editor key={`k-${key}`} value={jsonResult} ace={ace} theme="ace/theme/gob" />
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
  );
};

const mapStateToProps = ({ execute, authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
  result: execute.result,
});

const mapDispatchToProps = {
  interpretationEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Home);
