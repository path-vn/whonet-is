import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';
import { JsonEditor as Editor } from 'jsoneditor-react';
import ace from 'brace';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;
  const [json, setJson] = useState([{ orgCode: 'ppm', dataFields: null, test: [{ rawValue: '4', whonet5Code: 'AMC_NE', result: null }] }]);

  return (
    <Row>
      <Col md="5" className="pad">
        <Editor value={json} onChange={setJson} ace={ace} theme="ace/theme/github" />
        <button>Interpretation</button>
      </Col>
      <Col md="7">
        <h2>
          <Translate contentKey="home.title">Welcome to WHONET interpretation service!</Translate>
        </h2>

        {account && account.login ? (
          <div></div>
        ) : (
          <div>
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

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
