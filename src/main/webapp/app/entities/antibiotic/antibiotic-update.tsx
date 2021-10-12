import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './antibiotic.reducer';
import { IAntibiotic } from 'app/shared/model/antibiotic.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAntibioticUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AntibioticUpdate = (props: IAntibioticUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { antibioticEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/antibiotic' + props.location.search);
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
        ...antibioticEntity,
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
          <h2 id="amrInterpreationApp.antibiotic.home.createOrEditLabel" data-cy="AntibioticCreateUpdateHeading">
            <Translate contentKey="amrInterpreationApp.antibiotic.home.createOrEditLabel">Create or edit a Antibiotic</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : antibioticEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="antibiotic-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="antibiotic-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="whonetAbxCodeLabel" for="antibiotic-whonetAbxCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.whonetAbxCode">Whonet Abx Code</Translate>
                </Label>
                <AvField id="antibiotic-whonetAbxCode" data-cy="whonetAbxCode" type="text" name="whonetAbxCode" />
              </AvGroup>
              <AvGroup>
                <Label id="whoCodeLabel" for="antibiotic-whoCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.whoCode">Who Code</Translate>
                </Label>
                <AvField id="antibiotic-whoCode" data-cy="whoCode" type="text" name="whoCode" />
              </AvGroup>
              <AvGroup>
                <Label id="dinCodeLabel" for="antibiotic-dinCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.dinCode">Din Code</Translate>
                </Label>
                <AvField id="antibiotic-dinCode" data-cy="dinCode" type="text" name="dinCode" />
              </AvGroup>
              <AvGroup>
                <Label id="jacCodeLabel" for="antibiotic-jacCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.jacCode">Jac Code</Translate>
                </Label>
                <AvField id="antibiotic-jacCode" data-cy="jacCode" type="text" name="jacCode" />
              </AvGroup>
              <AvGroup>
                <Label id="eucastCodeLabel" for="antibiotic-eucastCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.eucastCode">Eucast Code</Translate>
                </Label>
                <AvField id="antibiotic-eucastCode" data-cy="eucastCode" type="text" name="eucastCode" />
              </AvGroup>
              <AvGroup>
                <Label id="userCodeLabel" for="antibiotic-userCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.userCode">User Code</Translate>
                </Label>
                <AvField id="antibiotic-userCode" data-cy="userCode" type="text" name="userCode" />
              </AvGroup>
              <AvGroup>
                <Label id="antibioticLabel" for="antibiotic-antibiotic">
                  <Translate contentKey="amrInterpreationApp.antibiotic.antibiotic">Antibiotic</Translate>
                </Label>
                <AvField id="antibiotic-antibiotic" data-cy="antibiotic" type="text" name="antibiotic" />
              </AvGroup>
              <AvGroup>
                <Label id="guidelinesLabel" for="antibiotic-guidelines">
                  <Translate contentKey="amrInterpreationApp.antibiotic.guidelines">Guidelines</Translate>
                </Label>
                <AvField id="antibiotic-guidelines" data-cy="guidelines" type="text" name="guidelines" />
              </AvGroup>
              <AvGroup>
                <Label id="clsiLabel" for="antibiotic-clsi">
                  <Translate contentKey="amrInterpreationApp.antibiotic.clsi">Clsi</Translate>
                </Label>
                <AvField id="antibiotic-clsi" data-cy="clsi" type="text" name="clsi" />
              </AvGroup>
              <AvGroup>
                <Label id="eucastLabel" for="antibiotic-eucast">
                  <Translate contentKey="amrInterpreationApp.antibiotic.eucast">Eucast</Translate>
                </Label>
                <AvField id="antibiotic-eucast" data-cy="eucast" type="text" name="eucast" />
              </AvGroup>
              <AvGroup>
                <Label id="sfmLabel" for="antibiotic-sfm">
                  <Translate contentKey="amrInterpreationApp.antibiotic.sfm">Sfm</Translate>
                </Label>
                <AvField id="antibiotic-sfm" data-cy="sfm" type="text" name="sfm" />
              </AvGroup>
              <AvGroup>
                <Label id="srgaLabel" for="antibiotic-srga">
                  <Translate contentKey="amrInterpreationApp.antibiotic.srga">Srga</Translate>
                </Label>
                <AvField id="antibiotic-srga" data-cy="srga" type="text" name="srga" />
              </AvGroup>
              <AvGroup>
                <Label id="bsacLabel" for="antibiotic-bsac">
                  <Translate contentKey="amrInterpreationApp.antibiotic.bsac">Bsac</Translate>
                </Label>
                <AvField id="antibiotic-bsac" data-cy="bsac" type="text" name="bsac" />
              </AvGroup>
              <AvGroup>
                <Label id="dinLabel" for="antibiotic-din">
                  <Translate contentKey="amrInterpreationApp.antibiotic.din">Din</Translate>
                </Label>
                <AvField id="antibiotic-din" data-cy="din" type="text" name="din" />
              </AvGroup>
              <AvGroup>
                <Label id="neoLabel" for="antibiotic-neo">
                  <Translate contentKey="amrInterpreationApp.antibiotic.neo">Neo</Translate>
                </Label>
                <AvField id="antibiotic-neo" data-cy="neo" type="text" name="neo" />
              </AvGroup>
              <AvGroup>
                <Label id="afaLabel" for="antibiotic-afa">
                  <Translate contentKey="amrInterpreationApp.antibiotic.afa">Afa</Translate>
                </Label>
                <AvField id="antibiotic-afa" data-cy="afa" type="text" name="afa" />
              </AvGroup>
              <AvGroup>
                <Label id="abxNumberLabel" for="antibiotic-abxNumber">
                  <Translate contentKey="amrInterpreationApp.antibiotic.abxNumber">Abx Number</Translate>
                </Label>
                <AvField id="antibiotic-abxNumber" data-cy="abxNumber" type="text" name="abxNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="potencyLabel" for="antibiotic-potency">
                  <Translate contentKey="amrInterpreationApp.antibiotic.potency">Potency</Translate>
                </Label>
                <AvField id="antibiotic-potency" data-cy="potency" type="text" name="potency" />
              </AvGroup>
              <AvGroup>
                <Label id="atcCodeLabel" for="antibiotic-atcCode">
                  <Translate contentKey="amrInterpreationApp.antibiotic.atcCode">Atc Code</Translate>
                </Label>
                <AvField id="antibiotic-atcCode" data-cy="atcCode" type="text" name="atcCode" />
              </AvGroup>
              <AvGroup>
                <Label id="profClassLabel" for="antibiotic-profClass">
                  <Translate contentKey="amrInterpreationApp.antibiotic.profClass">Prof Class</Translate>
                </Label>
                <AvField id="antibiotic-profClass" data-cy="profClass" type="text" name="profClass" />
              </AvGroup>
              <AvGroup>
                <Label id="ciaCategoryLabel" for="antibiotic-ciaCategory">
                  <Translate contentKey="amrInterpreationApp.antibiotic.ciaCategory">Cia Category</Translate>
                </Label>
                <AvField id="antibiotic-ciaCategory" data-cy="ciaCategory" type="text" name="ciaCategory" />
              </AvGroup>
              <AvGroup>
                <Label id="clsiOrderLabel" for="antibiotic-clsiOrder">
                  <Translate contentKey="amrInterpreationApp.antibiotic.clsiOrder">Clsi Order</Translate>
                </Label>
                <AvField id="antibiotic-clsiOrder" data-cy="clsiOrder" type="text" name="clsiOrder" />
              </AvGroup>
              <AvGroup>
                <Label id="eucastOrderLabel" for="antibiotic-eucastOrder">
                  <Translate contentKey="amrInterpreationApp.antibiotic.eucastOrder">Eucast Order</Translate>
                </Label>
                <AvField id="antibiotic-eucastOrder" data-cy="eucastOrder" type="text" name="eucastOrder" />
              </AvGroup>
              <AvGroup>
                <Label id="humanLabel" for="antibiotic-human">
                  <Translate contentKey="amrInterpreationApp.antibiotic.human">Human</Translate>
                </Label>
                <AvField id="antibiotic-human" data-cy="human" type="text" name="human" />
              </AvGroup>
              <AvGroup>
                <Label id="veterinaryLabel" for="antibiotic-veterinary">
                  <Translate contentKey="amrInterpreationApp.antibiotic.veterinary">Veterinary</Translate>
                </Label>
                <AvField id="antibiotic-veterinary" data-cy="veterinary" type="text" name="veterinary" />
              </AvGroup>
              <AvGroup>
                <Label id="animalGpLabel" for="antibiotic-animalGp">
                  <Translate contentKey="amrInterpreationApp.antibiotic.animalGp">Animal Gp</Translate>
                </Label>
                <AvField id="antibiotic-animalGp" data-cy="animalGp" type="text" name="animalGp" />
              </AvGroup>
              <AvGroup>
                <Label id="loinccompLabel" for="antibiotic-loinccomp">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loinccomp">Loinccomp</Translate>
                </Label>
                <AvField id="antibiotic-loinccomp" data-cy="loinccomp" type="text" name="loinccomp" />
              </AvGroup>
              <AvGroup>
                <Label id="loincgenLabel" for="antibiotic-loincgen">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincgen">Loincgen</Translate>
                </Label>
                <AvField id="antibiotic-loincgen" data-cy="loincgen" type="text" name="loincgen" />
              </AvGroup>
              <AvGroup>
                <Label id="loincdiskLabel" for="antibiotic-loincdisk">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincdisk">Loincdisk</Translate>
                </Label>
                <AvField id="antibiotic-loincdisk" data-cy="loincdisk" type="text" name="loincdisk" />
              </AvGroup>
              <AvGroup>
                <Label id="loincmicLabel" for="antibiotic-loincmic">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincmic">Loincmic</Translate>
                </Label>
                <AvField id="antibiotic-loincmic" data-cy="loincmic" type="text" name="loincmic" />
              </AvGroup>
              <AvGroup>
                <Label id="loincetestLabel" for="antibiotic-loincetest">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincetest">Loincetest</Translate>
                </Label>
                <AvField id="antibiotic-loincetest" data-cy="loincetest" type="text" name="loincetest" />
              </AvGroup>
              <AvGroup>
                <Label id="loincslowLabel" for="antibiotic-loincslow">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincslow">Loincslow</Translate>
                </Label>
                <AvField id="antibiotic-loincslow" data-cy="loincslow" type="text" name="loincslow" />
              </AvGroup>
              <AvGroup>
                <Label id="loincafbLabel" for="antibiotic-loincafb">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincafb">Loincafb</Translate>
                </Label>
                <AvField id="antibiotic-loincafb" data-cy="loincafb" type="text" name="loincafb" />
              </AvGroup>
              <AvGroup>
                <Label id="loincsbtLabel" for="antibiotic-loincsbt">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincsbt">Loincsbt</Translate>
                </Label>
                <AvField id="antibiotic-loincsbt" data-cy="loincsbt" type="text" name="loincsbt" />
              </AvGroup>
              <AvGroup>
                <Label id="loincmlcLabel" for="antibiotic-loincmlc">
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincmlc">Loincmlc</Translate>
                </Label>
                <AvField id="antibiotic-loincmlc" data-cy="loincmlc" type="text" name="loincmlc" />
              </AvGroup>
              <AvGroup>
                <Label id="dateEnteredLabel" for="antibiotic-dateEntered">
                  <Translate contentKey="amrInterpreationApp.antibiotic.dateEntered">Date Entered</Translate>
                </Label>
                <AvField id="antibiotic-dateEntered" data-cy="dateEntered" type="text" name="dateEntered" />
              </AvGroup>
              <AvGroup>
                <Label id="dateModifiedLabel" for="antibiotic-dateModified">
                  <Translate contentKey="amrInterpreationApp.antibiotic.dateModified">Date Modified</Translate>
                </Label>
                <AvField id="antibiotic-dateModified" data-cy="dateModified" type="text" name="dateModified" />
              </AvGroup>
              <AvGroup>
                <Label id="commentsLabel" for="antibiotic-comments">
                  <Translate contentKey="amrInterpreationApp.antibiotic.comments">Comments</Translate>
                </Label>
                <AvField id="antibiotic-comments" data-cy="comments" type="text" name="comments" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/antibiotic" replace color="info">
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
  antibioticEntity: storeState.antibiotic.entity,
  loading: storeState.antibiotic.loading,
  updating: storeState.antibiotic.updating,
  updateSuccess: storeState.antibiotic.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AntibioticUpdate);
