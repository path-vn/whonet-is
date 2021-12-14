import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './execute.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExecuteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExecuteDetail = (props: IExecuteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { executeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="executeDetailsHeading">
          <Translate contentKey="amrInterpreationApp.execute.detail.title">Execute</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{executeEntity.id}</dd>
          <dt>
            <span id="request">
              <Translate contentKey="amrInterpreationApp.execute.request">Request</Translate>
            </span>
          </dt>
          <dd>{executeEntity.request}</dd>
          <dt>
            <span id="response">
              <Translate contentKey="amrInterpreationApp.execute.response">Response</Translate>
            </span>
          </dt>
          <dd>{executeEntity.response}</dd>
          <dt>
            <span id="startedAt">
              <Translate contentKey="amrInterpreationApp.execute.startedAt">Started At</Translate>
            </span>
          </dt>
          <dd>{executeEntity.startedAt ? <TextFormat value={executeEntity.startedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="amrInterpreationApp.execute.time">Time</Translate>
            </span>
          </dt>
          <dd>{executeEntity.time}</dd>
        </dl>
        <Button tag={Link} to="/execute" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/execute/${executeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ execute }: IRootState) => ({
  executeEntity: execute.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExecuteDetail);
