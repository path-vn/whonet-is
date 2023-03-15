import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset, download } from './whonet-resource.reducer';
import { IWhonetResource } from 'app/shared/model/whonet-resource.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { empty, mapIdList } from 'app/shared/util/entity-utils';

export interface IWhonetResourceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WhonetResourceUpdate = (props: IWhonetResourceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);
  const [data, setData] = useState({});
  const { whonetResourceEntity, loading, updating } = props;

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

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.uploadDate = convertDateTimeToServer(values.uploadDate);

    if (errors.length === 0) {
      const entity = {
        ...whonetResourceEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity, data);
      } else {
        props.updateEntity(entity, data);
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
              <AvGroup className="padding-top-10">
                <Label id="antibioticLabel" for="whonet-resource-antibiotic">
                  <Translate contentKey="amrInterpreationApp.whonetResource.antibiotic">Antibiotic</Translate>
                </Label>
                <AvInput
                  name="a1"
                  className="inputfileNew"
                  type="file"
                  id="file"
                  onChange={evt => setData({ ...data, antibiotic: evt })}
                  style={{ float: 'right' }}
                />
                {!empty(whonetResourceEntity.antibiotic) && (
                  <a onClick={() => props.download(whonetResourceEntity.id, 'antibiotic')}>| Download antibiotic</a>
                )}
              </AvGroup>
              <AvGroup className="padding-top-10">
                <Label id="organismLabel" for="whonet-resource-organism">
                  <Translate contentKey="amrInterpreationApp.whonetResource.organism">Organism</Translate>
                </Label>
                <AvInput
                  name="o1"
                  className="inputfileNew"
                  type="file"
                  id="file"
                  onChange={evt => setData({ ...data, organism: evt })}
                  style={{ float: 'right' }}
                />
                {!empty(whonetResourceEntity.organism) && (
                  <a onClick={() => props.download(whonetResourceEntity.id, 'organism')}>| Download organism</a>
                )}
              </AvGroup>
              <AvGroup className="padding-top-10">
                <Label id="intrinsicResistanceLabel" for="whonet-resource-intrinsicResistance">
                  <Translate contentKey="amrInterpreationApp.whonetResource.intrinsicResistance">Intrinsic Resistance</Translate>
                </Label>
                <AvInput
                  name="i1"
                  className="inputfileNew"
                  type="file"
                  id="file"
                  onChange={evt => setData({ ...data, intrinsicResistance: evt })}
                  style={{ float: 'right' }}
                />

                {!empty(whonetResourceEntity.intrinsicResistance) && (
                  <a onClick={() => props.download(whonetResourceEntity.id, 'intrinsicResistance')}>| Download intrinsicResistance</a>
                )}
              </AvGroup>
              <AvGroup className="padding-top-10">
                <Label id="expertRuleLabel" for="whonet-resource-expertRule">
                  <Translate contentKey="amrInterpreationApp.whonetResource.expertRule">Expert Rule</Translate>
                </Label>

                <AvInput
                  name="e1"
                  className="inputfileNew"
                  type="file"
                  id="file"
                  onChange={evt => setData({ ...data, expertRule: evt })}
                  style={{ float: 'right' }}
                />

                {!empty(whonetResourceEntity.expertRule) && (
                  <a onClick={() => props.download(whonetResourceEntity.id, 'expertRule')}>| Download expertRule</a>
                )}
              </AvGroup>
              <AvGroup className="padding-top-10">
                <Label id="breakPointLabel" for="whonet-resource-breakPoint">
                  <Translate contentKey="amrInterpreationApp.whonetResource.breakPoint">Break Point</Translate>
                </Label>
                <AvInput
                  name="b1"
                  className="inputfileNew"
                  type="file"
                  id="file"
                  onChange={evt => setData({ ...data, breakPoint: evt })}
                  style={{ float: 'right' }}
                />

                {!empty(whonetResourceEntity.breakPoint) && (
                  <a onClick={() => props.download(whonetResourceEntity.id, 'breakPoint')}>| Download breakPoint</a>
                )}
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
  download,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WhonetResourceUpdate);
