import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './breakpoint.reducer';
import { IBreakpoint } from 'app/shared/model/breakpoint.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBreakpointUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BreakpointUpdate = (props: IBreakpointUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { breakpointEntity, loading, updating } = props;

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
                <Label id="guidelinesLabel" for="breakpoint-guidelines">
                  <Translate contentKey="amrInterpreationApp.breakpoint.guidelines">Guidelines</Translate>
                </Label>
                <AvField id="breakpoint-guidelines" data-cy="guidelines" type="text" name="guidelines" />
              </AvGroup>
              <AvGroup>
                <Label id="yearLabel" for="breakpoint-year">
                  <Translate contentKey="amrInterpreationApp.breakpoint.year">Year</Translate>
                </Label>
                <AvField id="breakpoint-year" data-cy="year" type="text" name="year" />
              </AvGroup>
              <AvGroup>
                <Label id="testMethodLabel" for="breakpoint-testMethod">
                  <Translate contentKey="amrInterpreationApp.breakpoint.testMethod">Test Method</Translate>
                </Label>
                <AvField id="breakpoint-testMethod" data-cy="testMethod" type="text" name="testMethod" />
              </AvGroup>
              <AvGroup>
                <Label id="potencyLabel" for="breakpoint-potency">
                  <Translate contentKey="amrInterpreationApp.breakpoint.potency">Potency</Translate>
                </Label>
                <AvField id="breakpoint-potency" data-cy="potency" type="text" name="potency" />
              </AvGroup>
              <AvGroup>
                <Label id="organismCodeLabel" for="breakpoint-organismCode">
                  <Translate contentKey="amrInterpreationApp.breakpoint.organismCode">Organism Code</Translate>
                </Label>
                <AvField id="breakpoint-organismCode" data-cy="organismCode" type="text" name="organismCode" />
              </AvGroup>
              <AvGroup>
                <Label id="organismCodeTypeLabel" for="breakpoint-organismCodeType">
                  <Translate contentKey="amrInterpreationApp.breakpoint.organismCodeType">Organism Code Type</Translate>
                </Label>
                <AvField id="breakpoint-organismCodeType" data-cy="organismCodeType" type="text" name="organismCodeType" />
              </AvGroup>
              <AvGroup>
                <Label id="breakpointTypeLabel" for="breakpoint-breakpointType">
                  <Translate contentKey="amrInterpreationApp.breakpoint.breakpointType">Breakpoint Type</Translate>
                </Label>
                <AvField id="breakpoint-breakpointType" data-cy="breakpointType" type="text" name="breakpointType" />
              </AvGroup>
              <AvGroup>
                <Label id="hostLabel" for="breakpoint-host">
                  <Translate contentKey="amrInterpreationApp.breakpoint.host">Host</Translate>
                </Label>
                <AvField id="breakpoint-host" data-cy="host" type="text" name="host" />
              </AvGroup>
              <AvGroup>
                <Label id="siteOfInfectionLabel" for="breakpoint-siteOfInfection">
                  <Translate contentKey="amrInterpreationApp.breakpoint.siteOfInfection">Site Of Infection</Translate>
                </Label>
                <AvField id="breakpoint-siteOfInfection" data-cy="siteOfInfection" type="text" name="siteOfInfection" />
              </AvGroup>
              <AvGroup>
                <Label id="referenceTableLabel" for="breakpoint-referenceTable">
                  <Translate contentKey="amrInterpreationApp.breakpoint.referenceTable">Reference Table</Translate>
                </Label>
                <AvField id="breakpoint-referenceTable" data-cy="referenceTable" type="text" name="referenceTable" />
              </AvGroup>
              <AvGroup>
                <Label id="referenceSequenceLabel" for="breakpoint-referenceSequence">
                  <Translate contentKey="amrInterpreationApp.breakpoint.referenceSequence">Reference Sequence</Translate>
                </Label>
                <AvField id="breakpoint-referenceSequence" data-cy="referenceSequence" type="text" name="referenceSequence" />
              </AvGroup>
              <AvGroup>
                <Label id="whonetAbxCodeLabel" for="breakpoint-whonetAbxCode">
                  <Translate contentKey="amrInterpreationApp.breakpoint.whonetAbxCode">Whonet Abx Code</Translate>
                </Label>
                <AvField id="breakpoint-whonetAbxCode" data-cy="whonetAbxCode" type="text" name="whonetAbxCode" />
              </AvGroup>
              <AvGroup>
                <Label id="whonetTestLabel" for="breakpoint-whonetTest">
                  <Translate contentKey="amrInterpreationApp.breakpoint.whonetTest">Whonet Test</Translate>
                </Label>
                <AvField id="breakpoint-whonetTest" data-cy="whonetTest" type="text" name="whonetTest" />
              </AvGroup>
              <AvGroup>
                <Label id="rLabel" for="breakpoint-r">
                  <Translate contentKey="amrInterpreationApp.breakpoint.r">R</Translate>
                </Label>
                <AvField id="breakpoint-r" data-cy="r" type="text" name="r" />
              </AvGroup>
              <AvGroup>
                <Label id="iLabel" for="breakpoint-i">
                  <Translate contentKey="amrInterpreationApp.breakpoint.i">I</Translate>
                </Label>
                <AvField id="breakpoint-i" data-cy="i" type="text" name="i" />
              </AvGroup>
              <AvGroup>
                <Label id="sddLabel" for="breakpoint-sdd">
                  <Translate contentKey="amrInterpreationApp.breakpoint.sdd">Sdd</Translate>
                </Label>
                <AvField id="breakpoint-sdd" data-cy="sdd" type="text" name="sdd" />
              </AvGroup>
              <AvGroup>
                <Label id="sLabel" for="breakpoint-s">
                  <Translate contentKey="amrInterpreationApp.breakpoint.s">S</Translate>
                </Label>
                <AvField id="breakpoint-s" data-cy="s" type="text" name="s" />
              </AvGroup>
              <AvGroup>
                <Label id="ecvEcoffLabel" for="breakpoint-ecvEcoff">
                  <Translate contentKey="amrInterpreationApp.breakpoint.ecvEcoff">Ecv Ecoff</Translate>
                </Label>
                <AvField id="breakpoint-ecvEcoff" data-cy="ecvEcoff" type="text" name="ecvEcoff" />
              </AvGroup>
              <AvGroup>
                <Label id="dateEnteredLabel" for="breakpoint-dateEntered">
                  <Translate contentKey="amrInterpreationApp.breakpoint.dateEntered">Date Entered</Translate>
                </Label>
                <AvField id="breakpoint-dateEntered" data-cy="dateEntered" type="text" name="dateEntered" />
              </AvGroup>
              <AvGroup>
                <Label id="dateModifiedLabel" for="breakpoint-dateModified">
                  <Translate contentKey="amrInterpreationApp.breakpoint.dateModified">Date Modified</Translate>
                </Label>
                <AvField id="breakpoint-dateModified" data-cy="dateModified" type="text" name="dateModified" />
              </AvGroup>
              <AvGroup>
                <Label id="commentsLabel" for="breakpoint-comments">
                  <Translate contentKey="amrInterpreationApp.breakpoint.comments">Comments</Translate>
                </Label>
                <AvField id="breakpoint-comments" data-cy="comments" type="text" name="comments" />
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
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BreakpointUpdate);
