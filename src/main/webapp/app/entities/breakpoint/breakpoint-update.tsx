import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './breakpoint.reducer';
import { IBreakpoint } from 'app/shared/model/breakpoint.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBreakpointUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BreakpointUpdate = (props: IBreakpointUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { breakpointEntity, loading, updating } = props;

  const { binaryData, binaryDataContentType } = breakpointEntity;

  const handleClose = () => {
    props.history.push('/breakpoint' + props.location.search);
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
    if (errors.length === 0) {
      const entity = {
        ...breakpointEntity,
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
          <h2 id="amrInterpreationApp.breakpoint.home.createOrEditLabel" data-cy="BreakpointCreateUpdateHeading">
            <Translate contentKey="amrInterpreationApp.breakpoint.home.createOrEditLabel">Create or edit a Breakpoint</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : breakpointEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="breakpoint-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="breakpoint-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="pathLabel" for="breakpoint-path">
                  <Translate contentKey="amrInterpreationApp.breakpoint.path">Path</Translate>
                </Label>
                <AvField id="breakpoint-path" data-cy="path" type="text" name="path" />
              </AvGroup>
              <AvGroup>
                <Label id="queryLabel" for="breakpoint-query">
                  <Translate contentKey="amrInterpreationApp.breakpoint.query">Query</Translate>
                </Label>
                <AvField id="breakpoint-query" data-cy="query" type="text" name="query" />
              </AvGroup>
              <AvGroup>
                <Label id="antibioticQueryLabel" for="breakpoint-antibioticQuery">
                  <Translate contentKey="amrInterpreationApp.breakpoint.antibioticQuery">Antibiotic Query</Translate>
                </Label>
                <AvField id="breakpoint-antibioticQuery" data-cy="antibioticQuery" type="text" name="antibioticQuery" />
              </AvGroup>
              <AvGroup>
                <Label id="organismQueryLabel" for="breakpoint-organismQuery">
                  <Translate contentKey="amrInterpreationApp.breakpoint.organismQuery">Organism Query</Translate>
                </Label>
                <AvField id="breakpoint-organismQuery" data-cy="organismQuery" type="text" name="organismQuery" />
              </AvGroup>
              <AvGroup>
                <Label id="intrinsicResistanceQueryLabel" for="breakpoint-intrinsicResistanceQuery">
                  <Translate contentKey="amrInterpreationApp.breakpoint.intrinsicResistanceQuery">Intrinsic Resistance Query</Translate>
                </Label>
                <AvField
                  id="breakpoint-intrinsicResistanceQuery"
                  data-cy="intrinsicResistanceQuery"
                  type="text"
                  name="intrinsicResistanceQuery"
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="binaryDataLabel" for="binaryData">
                    <Translate contentKey="amrInterpreationApp.breakpoint.binaryData">Binary Data</Translate>
                  </Label>
                  <br />
                  {binaryData ? (
                    <div>
                      {binaryDataContentType ? (
                        <a onClick={openFile(binaryDataContentType, binaryData)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {binaryDataContentType}, {byteSize(binaryData)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('binaryData')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_binaryData" data-cy="binaryData" type="file" onChange={onBlobChange(false, 'binaryData')} />
                  <AvInput type="hidden" name="binaryData" value={binaryData} />
                </AvGroup>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/breakpoint" replace color="info">
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
  breakpointEntity: storeState.breakpoint.entity,
  loading: storeState.breakpoint.loading,
  updating: storeState.breakpoint.updating,
  updateSuccess: storeState.breakpoint.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(BreakpointUpdate);
