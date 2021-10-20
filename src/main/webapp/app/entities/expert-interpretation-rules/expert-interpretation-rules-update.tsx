import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './expert-interpretation-rules.reducer';
import { IExpertInterpretationRules } from 'app/shared/model/expert-interpretation-rules.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExpertInterpretationRulesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExpertInterpretationRulesUpdate = (props: IExpertInterpretationRulesUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { expertInterpretationRulesEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/expert-interpretation-rules' + props.location.search);
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
        ...expertInterpretationRulesEntity,
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
          <h2
            id="amrInterpreationApp.expertInterpretationRules.home.createOrEditLabel"
            data-cy="ExpertInterpretationRulesCreateUpdateHeading"
          >
            <Translate contentKey="amrInterpreationApp.expertInterpretationRules.home.createOrEditLabel">
              Create or edit a ExpertInterpretationRules
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : expertInterpretationRulesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="expert-interpretation-rules-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="expert-interpretation-rules-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="ruleCodeLabel" for="expert-interpretation-rules-ruleCode">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.ruleCode">Rule Code</Translate>
                </Label>
                <AvField id="expert-interpretation-rules-ruleCode" data-cy="ruleCode" type="text" name="ruleCode" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="expert-interpretation-rules-description">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.description">Description</Translate>
                </Label>
                <AvField id="expert-interpretation-rules-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="organismCodeLabel" for="expert-interpretation-rules-organismCode">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.organismCode">Organism Code</Translate>
                </Label>
                <AvField id="expert-interpretation-rules-organismCode" data-cy="organismCode" type="text" name="organismCode" />
              </AvGroup>
              <AvGroup>
                <Label id="organismCodeTypeLabel" for="expert-interpretation-rules-organismCodeType">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.organismCodeType">Organism Code Type</Translate>
                </Label>
                <AvField id="expert-interpretation-rules-organismCodeType" data-cy="organismCodeType" type="text" name="organismCodeType" />
              </AvGroup>
              <AvGroup>
                <Label id="ruleCriteriaLabel" for="expert-interpretation-rules-ruleCriteria">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.ruleCriteria">Rule Criteria</Translate>
                </Label>
                <AvField id="expert-interpretation-rules-ruleCriteria" data-cy="ruleCriteria" type="text" name="ruleCriteria" />
              </AvGroup>
              <AvGroup>
                <Label id="affectedAntibioticsLabel" for="expert-interpretation-rules-affectedAntibiotics">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.affectedAntibiotics">Affected Antibiotics</Translate>
                </Label>
                <AvField
                  id="expert-interpretation-rules-affectedAntibiotics"
                  data-cy="affectedAntibiotics"
                  type="text"
                  name="affectedAntibiotics"
                />
              </AvGroup>
              <AvGroup>
                <Label id="antibioticExceptionsLabel" for="expert-interpretation-rules-antibioticExceptions">
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.antibioticExceptions">
                    Antibiotic Exceptions
                  </Translate>
                </Label>
                <AvField
                  id="expert-interpretation-rules-antibioticExceptions"
                  data-cy="antibioticExceptions"
                  type="text"
                  name="antibioticExceptions"
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/expert-interpretation-rules" replace color="info">
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
  expertInterpretationRulesEntity: storeState.expertInterpretationRules.entity,
  loading: storeState.expertInterpretationRules.loading,
  updating: storeState.expertInterpretationRules.updating,
  updateSuccess: storeState.expertInterpretationRules.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExpertInterpretationRulesUpdate);
