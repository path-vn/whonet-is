import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './whonet-resource.reducer';
import { IWhonetResource } from 'app/shared/model/whonet-resource.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWhonetResourceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WhonetResourceUpdate = (props: IWhonetResourceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { whonetResourceEntity, loading, updating } = props;

  const { message } = whonetResourceEntity;

  const handleClose = () => {
    props.history.push('/whonet-resource' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.uploadDate = convertDateTimeToServer(values.uploadDate);
    values.importedDate = convertDateTimeToServer(values.importedDate);

    if (errors.length === 0) {
      const entity = {
        ...whonetResourceEntity,
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
          <h2 id="amrInterpreationApp.whonetResource.home.createOrEditLabel" data-cy="WhonetResourceCreateUpdateHeading">
            <Translate contentKey="amrInterpreationApp.whonetResource.home.createOrEditLabel">Create or edit a WhonetResource</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : whonetResourceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="whonet-resource-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="whonet-resource-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="uploadDateLabel" for="whonet-resource-uploadDate">
                  <Translate contentKey="amrInterpreationApp.whonetResource.uploadDate">Upload Date</Translate>
                </Label>
                <AvInput
                  id="whonet-resource-uploadDate"
                  data-cy="uploadDate"
                  type="datetime-local"
                  className="form-control"
                  name="uploadDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.whonetResourceEntity.uploadDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="antibioticLabel" for="whonet-resource-antibiotic">
                  <Translate contentKey="amrInterpreationApp.whonetResource.antibiotic">Antibiotic</Translate>
                </Label>
                <AvField id="whonet-resource-antibiotic" data-cy="antibiotic" type="text" name="antibiotic" />
              </AvGroup>
              <AvGroup>
                <Label id="organismLabel" for="whonet-resource-organism">
                  <Translate contentKey="amrInterpreationApp.whonetResource.organism">Organism</Translate>
                </Label>
                <AvField id="whonet-resource-organism" data-cy="organism" type="text" name="organism" />
              </AvGroup>
              <AvGroup>
                <Label id="intrinsicResistanceLabel" for="whonet-resource-intrinsicResistance">
                  <Translate contentKey="amrInterpreationApp.whonetResource.intrinsicResistance">Intrinsic Resistance</Translate>
                </Label>
                <AvField id="whonet-resource-intrinsicResistance" data-cy="intrinsicResistance" type="text" name="intrinsicResistance" />
              </AvGroup>
              <AvGroup>
                <Label id="expertRuleLabel" for="whonet-resource-expertRule">
                  <Translate contentKey="amrInterpreationApp.whonetResource.expertRule">Expert Rule</Translate>
                </Label>
                <AvField id="whonet-resource-expertRule" data-cy="expertRule" type="text" name="expertRule" />
              </AvGroup>
              <AvGroup>
                <Label id="breakPointLabel" for="whonet-resource-breakPoint">
                  <Translate contentKey="amrInterpreationApp.whonetResource.breakPoint">Break Point</Translate>
                </Label>
                <AvField id="whonet-resource-breakPoint" data-cy="breakPoint" type="text" name="breakPoint" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="whonet-resource-status">
                  <Translate contentKey="amrInterpreationApp.whonetResource.status">Status</Translate>
                </Label>
                <AvField id="whonet-resource-status" data-cy="status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="importedDateLabel" for="whonet-resource-importedDate">
                  <Translate contentKey="amrInterpreationApp.whonetResource.importedDate">Imported Date</Translate>
                </Label>
                <AvInput
                  id="whonet-resource-importedDate"
                  data-cy="importedDate"
                  type="datetime-local"
                  className="form-control"
                  name="importedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.whonetResourceEntity.importedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="messageLabel" for="whonet-resource-message">
                  <Translate contentKey="amrInterpreationApp.whonetResource.message">Message</Translate>
                </Label>
                <AvInput id="whonet-resource-message" data-cy="message" type="textarea" name="message" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/whonet-resource" replace color="info">
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
  whonetResourceEntity: storeState.whonetResource.entity,
  loading: storeState.whonetResource.loading,
  updating: storeState.whonetResource.updating,
  updateSuccess: storeState.whonetResource.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WhonetResourceUpdate);
