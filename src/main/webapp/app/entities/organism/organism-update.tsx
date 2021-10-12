import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './organism.reducer';
import { IOrganism } from 'app/shared/model/organism.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrganismUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrganismUpdate = (props: IOrganismUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { organismEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/organism' + props.location.search);
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
        ...organismEntity,
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
          <h2 id="amrInterpreationApp.organism.home.createOrEditLabel" data-cy="OrganismCreateUpdateHeading">
            <Translate contentKey="amrInterpreationApp.organism.home.createOrEditLabel">Create or edit a Organism</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : organismEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="organism-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="organism-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="whonetOrgCodeLabel" for="organism-whonetOrgCode">
                  <Translate contentKey="amrInterpreationApp.organism.whonetOrgCode">Whonet Org Code</Translate>
                </Label>
                <AvField id="organism-whonetOrgCode" data-cy="whonetOrgCode" type="text" name="whonetOrgCode" />
              </AvGroup>
              <AvGroup>
                <Label id="organismLabel" for="organism-organism">
                  <Translate contentKey="amrInterpreationApp.organism.organism">Organism</Translate>
                </Label>
                <AvField id="organism-organism" data-cy="organism" type="text" name="organism" />
              </AvGroup>
              <AvGroup>
                <Label id="taxonomicStatusLabel" for="organism-taxonomicStatus">
                  <Translate contentKey="amrInterpreationApp.organism.taxonomicStatus">Taxonomic Status</Translate>
                </Label>
                <AvField id="organism-taxonomicStatus" data-cy="taxonomicStatus" type="text" name="taxonomicStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="commonLabel" for="organism-common">
                  <Translate contentKey="amrInterpreationApp.organism.common">Common</Translate>
                </Label>
                <AvField id="organism-common" data-cy="common" type="text" name="common" />
              </AvGroup>
              <AvGroup>
                <Label id="organismTypeLabel" for="organism-organismType">
                  <Translate contentKey="amrInterpreationApp.organism.organismType">Organism Type</Translate>
                </Label>
                <AvField id="organism-organismType" data-cy="organismType" type="text" name="organismType" />
              </AvGroup>
              <AvGroup>
                <Label id="anaerobeLabel" for="organism-anaerobe">
                  <Translate contentKey="amrInterpreationApp.organism.anaerobe">Anaerobe</Translate>
                </Label>
                <AvField id="organism-anaerobe" data-cy="anaerobe" type="text" name="anaerobe" />
              </AvGroup>
              <AvGroup>
                <Label id="morphologyLabel" for="organism-morphology">
                  <Translate contentKey="amrInterpreationApp.organism.morphology">Morphology</Translate>
                </Label>
                <AvField id="organism-morphology" data-cy="morphology" type="text" name="morphology" />
              </AvGroup>
              <AvGroup>
                <Label id="subkingdomCodeLabel" for="organism-subkingdomCode">
                  <Translate contentKey="amrInterpreationApp.organism.subkingdomCode">Subkingdom Code</Translate>
                </Label>
                <AvField id="organism-subkingdomCode" data-cy="subkingdomCode" type="text" name="subkingdomCode" />
              </AvGroup>
              <AvGroup>
                <Label id="familyCodeLabel" for="organism-familyCode">
                  <Translate contentKey="amrInterpreationApp.organism.familyCode">Family Code</Translate>
                </Label>
                <AvField id="organism-familyCode" data-cy="familyCode" type="text" name="familyCode" />
              </AvGroup>
              <AvGroup>
                <Label id="genusGroupLabel" for="organism-genusGroup">
                  <Translate contentKey="amrInterpreationApp.organism.genusGroup">Genus Group</Translate>
                </Label>
                <AvField id="organism-genusGroup" data-cy="genusGroup" type="text" name="genusGroup" />
              </AvGroup>
              <AvGroup>
                <Label id="genusCodeLabel" for="organism-genusCode">
                  <Translate contentKey="amrInterpreationApp.organism.genusCode">Genus Code</Translate>
                </Label>
                <AvField id="organism-genusCode" data-cy="genusCode" type="text" name="genusCode" />
              </AvGroup>
              <AvGroup>
                <Label id="speciesGroupLabel" for="organism-speciesGroup">
                  <Translate contentKey="amrInterpreationApp.organism.speciesGroup">Species Group</Translate>
                </Label>
                <AvField id="organism-speciesGroup" data-cy="speciesGroup" type="text" name="speciesGroup" />
              </AvGroup>
              <AvGroup>
                <Label id="serovarGroupLabel" for="organism-serovarGroup">
                  <Translate contentKey="amrInterpreationApp.organism.serovarGroup">Serovar Group</Translate>
                </Label>
                <AvField id="organism-serovarGroup" data-cy="serovarGroup" type="text" name="serovarGroup" />
              </AvGroup>
              <AvGroup>
                <Label id="msfGrpClinLabel" for="organism-msfGrpClin">
                  <Translate contentKey="amrInterpreationApp.organism.msfGrpClin">Msf Grp Clin</Translate>
                </Label>
                <AvField id="organism-msfGrpClin" data-cy="msfGrpClin" type="text" name="msfGrpClin" />
              </AvGroup>
              <AvGroup>
                <Label id="sctCodeLabel" for="organism-sctCode">
                  <Translate contentKey="amrInterpreationApp.organism.sctCode">Sct Code</Translate>
                </Label>
                <AvField id="organism-sctCode" data-cy="sctCode" type="text" name="sctCode" />
              </AvGroup>
              <AvGroup>
                <Label id="sctTextLabel" for="organism-sctText">
                  <Translate contentKey="amrInterpreationApp.organism.sctText">Sct Text</Translate>
                </Label>
                <AvField id="organism-sctText" data-cy="sctText" type="text" name="sctText" />
              </AvGroup>
              <AvGroup>
                <Label id="dwcTaxonIdLabel" for="organism-dwcTaxonId">
                  <Translate contentKey="amrInterpreationApp.organism.dwcTaxonId">Dwc Taxon Id</Translate>
                </Label>
                <AvField id="organism-dwcTaxonId" data-cy="dwcTaxonId" type="text" name="dwcTaxonId" />
              </AvGroup>
              <AvGroup>
                <Label id="dwcTaxonomicStatusLabel" for="organism-dwcTaxonomicStatus">
                  <Translate contentKey="amrInterpreationApp.organism.dwcTaxonomicStatus">Dwc Taxonomic Status</Translate>
                </Label>
                <AvField id="organism-dwcTaxonomicStatus" data-cy="dwcTaxonomicStatus" type="text" name="dwcTaxonomicStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="gbifTaxonIdLabel" for="organism-gbifTaxonId">
                  <Translate contentKey="amrInterpreationApp.organism.gbifTaxonId">Gbif Taxon Id</Translate>
                </Label>
                <AvField id="organism-gbifTaxonId" data-cy="gbifTaxonId" type="text" name="gbifTaxonId" />
              </AvGroup>
              <AvGroup>
                <Label id="gbifDatasetIdLabel" for="organism-gbifDatasetId">
                  <Translate contentKey="amrInterpreationApp.organism.gbifDatasetId">Gbif Dataset Id</Translate>
                </Label>
                <AvField id="organism-gbifDatasetId" data-cy="gbifDatasetId" type="text" name="gbifDatasetId" />
              </AvGroup>
              <AvGroup>
                <Label id="gbifTaxonomicStatusLabel" for="organism-gbifTaxonomicStatus">
                  <Translate contentKey="amrInterpreationApp.organism.gbifTaxonomicStatus">Gbif Taxonomic Status</Translate>
                </Label>
                <AvField id="organism-gbifTaxonomicStatus" data-cy="gbifTaxonomicStatus" type="text" name="gbifTaxonomicStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="kingdomLabel" for="organism-kingdom">
                  <Translate contentKey="amrInterpreationApp.organism.kingdom">Kingdom</Translate>
                </Label>
                <AvField id="organism-kingdom" data-cy="kingdom" type="text" name="kingdom" />
              </AvGroup>
              <AvGroup>
                <Label id="phylumLabel" for="organism-phylum">
                  <Translate contentKey="amrInterpreationApp.organism.phylum">Phylum</Translate>
                </Label>
                <AvField id="organism-phylum" data-cy="phylum" type="text" name="phylum" />
              </AvGroup>
              <AvGroup>
                <Label id="organismClassLabel" for="organism-organismClass">
                  <Translate contentKey="amrInterpreationApp.organism.organismClass">Organism Class</Translate>
                </Label>
                <AvField id="organism-organismClass" data-cy="organismClass" type="text" name="organismClass" />
              </AvGroup>
              <AvGroup>
                <Label id="orderLabel" for="organism-order">
                  <Translate contentKey="amrInterpreationApp.organism.order">Order</Translate>
                </Label>
                <AvField id="organism-order" data-cy="order" type="text" name="order" />
              </AvGroup>
              <AvGroup>
                <Label id="familyLabel" for="organism-family">
                  <Translate contentKey="amrInterpreationApp.organism.family">Family</Translate>
                </Label>
                <AvField id="organism-family" data-cy="family" type="text" name="family" />
              </AvGroup>
              <AvGroup>
                <Label id="genusLabel" for="organism-genus">
                  <Translate contentKey="amrInterpreationApp.organism.genus">Genus</Translate>
                </Label>
                <AvField id="organism-genus" data-cy="genus" type="text" name="genus" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/organism" replace color="info">
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
  organismEntity: storeState.organism.entity,
  loading: storeState.organism.loading,
  updating: storeState.organism.updating,
  updateSuccess: storeState.organism.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrganismUpdate);
