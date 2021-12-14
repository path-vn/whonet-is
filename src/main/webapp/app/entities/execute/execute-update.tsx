import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './execute.reducer';
import { IExecute } from 'app/shared/model/execute.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExecuteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExecuteUpdate = (props: IExecuteUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { executeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/execute' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startedAt = convertDateTimeToServer(values.startedAt);

    if (errors.length === 0) {
      const entity = {
        ...executeEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="amrInterpreationApp.execute.home.createOrEditLabel" data-cy="ExecuteCreateUpdateHeading">
            <Translate contentKey="amrInterpreationApp.execute.home.createOrEditLabel">Create or edit a Execute</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : executeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="execute-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="execute-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="requestLabel" for="execute-request">
                  <Translate contentKey="amrInterpreationApp.execute.request">Request</Translate>
                </Label>
                <AvField id="execute-request" data-cy="request" type="text" name="request" />
              </AvGroup>
              <AvGroup>
                <Label id="responseLabel" for="execute-response">
                  <Translate contentKey="amrInterpreationApp.execute.response">Response</Translate>
                </Label>
                <AvField id="execute-response" data-cy="response" type="text" name="response" />
              </AvGroup>
              <AvGroup>
                <Label id="startedAtLabel" for="execute-startedAt">
                  <Translate contentKey="amrInterpreationApp.execute.startedAt">Started At</Translate>
                </Label>
                <AvInput
                  id="execute-startedAt"
                  data-cy="startedAt"
                  type="datetime-local"
                  className="form-control"
                  name="startedAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.executeEntity.startedAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="timeLabel" for="execute-time">
                  <Translate contentKey="amrInterpreationApp.execute.time">Time</Translate>
                </Label>
                <AvField id="execute-time" data-cy="time" type="string" className="form-control" name="time" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/execute" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  executeEntity: storeState.execute.entity,
  loading: storeState.execute.loading,
  updating: storeState.execute.updating,
  updateSuccess: storeState.execute.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExecuteUpdate);
