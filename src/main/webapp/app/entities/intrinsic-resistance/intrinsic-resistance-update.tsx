import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './intrinsic-resistance.reducer';
import { IIntrinsicResistance } from 'app/shared/model/intrinsic-resistance.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IIntrinsicResistanceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IntrinsicResistanceUpdate = (props: IIntrinsicResistanceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { intrinsicResistanceEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/intrinsic-resistance' + props.location.search);
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
        ...intrinsicResistanceEntity,
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
          <h2 id="amrInterpreationApp.intrinsicResistance.home.createOrEditLabel" data-cy="IntrinsicResistanceCreateUpdateHeading">
            <Translate contentKey="amrInterpreationApp.intrinsicResistance.home.createOrEditLabel">
              Create or edit a IntrinsicResistance
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : intrinsicResistanceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="intrinsic-resistance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="intrinsic-resistance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="guidelineLabel" for="intrinsic-resistance-guideline">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.guideline">Guideline</Translate>
                </Label>
                <AvField id="intrinsic-resistance-guideline" data-cy="guideline" type="text" name="guideline" />
              </AvGroup>
              <AvGroup>
                <Label id="referenceTableLabel" for="intrinsic-resistance-referenceTable">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.referenceTable">Reference Table</Translate>
                </Label>
                <AvField id="intrinsic-resistance-referenceTable" data-cy="referenceTable" type="text" name="referenceTable" />
              </AvGroup>
              <AvGroup>
                <Label id="organismCodeLabel" for="intrinsic-resistance-organismCode">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.organismCode">Organism Code</Translate>
                </Label>
                <AvField id="intrinsic-resistance-organismCode" data-cy="organismCode" type="text" name="organismCode" />
              </AvGroup>
              <AvGroup>
                <Label id="organismCodeTypeLabel" for="intrinsic-resistance-organismCodeType">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.organismCodeType">Organism Code Type</Translate>
                </Label>
                <AvField id="intrinsic-resistance-organismCodeType" data-cy="organismCodeType" type="text" name="organismCodeType" />
              </AvGroup>
              <AvGroup>
                <Label id="exceptionOrganismCodeLabel" for="intrinsic-resistance-exceptionOrganismCode">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.exceptionOrganismCode">Exception Organism Code</Translate>
                </Label>
                <AvField
                  id="intrinsic-resistance-exceptionOrganismCode"
                  data-cy="exceptionOrganismCode"
                  type="text"
                  name="exceptionOrganismCode"
                />
              </AvGroup>
              <AvGroup>
                <Label id="exceptionOrganismCodeTypeLabel" for="intrinsic-resistance-exceptionOrganismCodeType">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.exceptionOrganismCodeType">
                    Exception Organism Code Type
                  </Translate>
                </Label>
                <AvField
                  id="intrinsic-resistance-exceptionOrganismCodeType"
                  data-cy="exceptionOrganismCodeType"
                  type="text"
                  name="exceptionOrganismCodeType"
                />
              </AvGroup>
              <AvGroup>
                <Label id="abxCodeLabel" for="intrinsic-resistance-abxCode">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.abxCode">Abx Code</Translate>
                </Label>
                <AvField id="intrinsic-resistance-abxCode" data-cy="abxCode" type="text" name="abxCode" />
              </AvGroup>
              <AvGroup>
                <Label id="abxCodeTypeLabel" for="intrinsic-resistance-abxCodeType">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.abxCodeType">Abx Code Type</Translate>
                </Label>
                <AvField id="intrinsic-resistance-abxCodeType" data-cy="abxCodeType" type="text" name="abxCodeType" />
              </AvGroup>
              <AvGroup>
                <Label id="antibioticExceptionsLabel" for="intrinsic-resistance-antibioticExceptions">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.antibioticExceptions">Antibiotic Exceptions</Translate>
                </Label>
                <AvField
                  id="intrinsic-resistance-antibioticExceptions"
                  data-cy="antibioticExceptions"
                  type="text"
                  name="antibioticExceptions"
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateEnteredLabel" for="intrinsic-resistance-dateEntered">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.dateEntered">Date Entered</Translate>
                </Label>
                <AvField id="intrinsic-resistance-dateEntered" data-cy="dateEntered" type="text" name="dateEntered" />
              </AvGroup>
              <AvGroup>
                <Label id="dateModifiedLabel" for="intrinsic-resistance-dateModified">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.dateModified">Date Modified</Translate>
                </Label>
                <AvField id="intrinsic-resistance-dateModified" data-cy="dateModified" type="text" name="dateModified" />
              </AvGroup>
              <AvGroup>
                <Label id="commentsLabel" for="intrinsic-resistance-comments">
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.comments">Comments</Translate>
                </Label>
                <AvField id="intrinsic-resistance-comments" data-cy="comments" type="text" name="comments" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/intrinsic-resistance" replace color="info">
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
  intrinsicResistanceEntity: storeState.intrinsicResistance.entity,
  loading: storeState.intrinsicResistance.loading,
  updating: storeState.intrinsicResistance.updating,
  updateSuccess: storeState.intrinsicResistance.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IntrinsicResistanceUpdate);
