import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './organism.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrganismDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrganismDetail = (props: IOrganismDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { organismEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organismDetailsHeading">
          <Translate contentKey="amrInterpreationApp.organism.detail.title">Organism</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{organismEntity.id}</dd>
          <dt>
            <span id="whonetOrgCode">
              <Translate contentKey="amrInterpreationApp.organism.whonetOrgCode">Whonet Org Code</Translate>
            </span>
          </dt>
          <dd>{organismEntity.whonetOrgCode}</dd>
          <dt>
            <span id="organism">
              <Translate contentKey="amrInterpreationApp.organism.organism">Organism</Translate>
            </span>
          </dt>
          <dd>{organismEntity.organism}</dd>
          <dt>
            <span id="taxonomicStatus">
              <Translate contentKey="amrInterpreationApp.organism.taxonomicStatus">Taxonomic Status</Translate>
            </span>
          </dt>
          <dd>{organismEntity.taxonomicStatus}</dd>
          <dt>
            <span id="common">
              <Translate contentKey="amrInterpreationApp.organism.common">Common</Translate>
            </span>
          </dt>
          <dd>{organismEntity.common}</dd>
          <dt>
            <span id="organismType">
              <Translate contentKey="amrInterpreationApp.organism.organismType">Organism Type</Translate>
            </span>
          </dt>
          <dd>{organismEntity.organismType}</dd>
          <dt>
            <span id="anaerobe">
              <Translate contentKey="amrInterpreationApp.organism.anaerobe">Anaerobe</Translate>
            </span>
          </dt>
          <dd>{organismEntity.anaerobe}</dd>
          <dt>
            <span id="morphology">
              <Translate contentKey="amrInterpreationApp.organism.morphology">Morphology</Translate>
            </span>
          </dt>
          <dd>{organismEntity.morphology}</dd>
          <dt>
            <span id="subkingdomCode">
              <Translate contentKey="amrInterpreationApp.organism.subkingdomCode">Subkingdom Code</Translate>
            </span>
          </dt>
          <dd>{organismEntity.subkingdomCode}</dd>
          <dt>
            <span id="familyCode">
              <Translate contentKey="amrInterpreationApp.organism.familyCode">Family Code</Translate>
            </span>
          </dt>
          <dd>{organismEntity.familyCode}</dd>
          <dt>
            <span id="genusGroup">
              <Translate contentKey="amrInterpreationApp.organism.genusGroup">Genus Group</Translate>
            </span>
          </dt>
          <dd>{organismEntity.genusGroup}</dd>
          <dt>
            <span id="genusCode">
              <Translate contentKey="amrInterpreationApp.organism.genusCode">Genus Code</Translate>
            </span>
          </dt>
          <dd>{organismEntity.genusCode}</dd>
          <dt>
            <span id="speciesGroup">
              <Translate contentKey="amrInterpreationApp.organism.speciesGroup">Species Group</Translate>
            </span>
          </dt>
          <dd>{organismEntity.speciesGroup}</dd>
          <dt>
            <span id="serovarGroup">
              <Translate contentKey="amrInterpreationApp.organism.serovarGroup">Serovar Group</Translate>
            </span>
          </dt>
          <dd>{organismEntity.serovarGroup}</dd>
          <dt>
            <span id="msfGrpClin">
              <Translate contentKey="amrInterpreationApp.organism.msfGrpClin">Msf Grp Clin</Translate>
            </span>
          </dt>
          <dd>{organismEntity.msfGrpClin}</dd>
          <dt>
            <span id="sctCode">
              <Translate contentKey="amrInterpreationApp.organism.sctCode">Sct Code</Translate>
            </span>
          </dt>
          <dd>{organismEntity.sctCode}</dd>
          <dt>
            <span id="sctText">
              <Translate contentKey="amrInterpreationApp.organism.sctText">Sct Text</Translate>
            </span>
          </dt>
          <dd>{organismEntity.sctText}</dd>
          <dt>
            <span id="dwcTaxonId">
              <Translate contentKey="amrInterpreationApp.organism.dwcTaxonId">Dwc Taxon Id</Translate>
            </span>
          </dt>
          <dd>{organismEntity.dwcTaxonId}</dd>
          <dt>
            <span id="dwcTaxonomicStatus">
              <Translate contentKey="amrInterpreationApp.organism.dwcTaxonomicStatus">Dwc Taxonomic Status</Translate>
            </span>
          </dt>
          <dd>{organismEntity.dwcTaxonomicStatus}</dd>
          <dt>
            <span id="gbifTaxonId">
              <Translate contentKey="amrInterpreationApp.organism.gbifTaxonId">Gbif Taxon Id</Translate>
            </span>
          </dt>
          <dd>{organismEntity.gbifTaxonId}</dd>
          <dt>
            <span id="gbifDatasetId">
              <Translate contentKey="amrInterpreationApp.organism.gbifDatasetId">Gbif Dataset Id</Translate>
            </span>
          </dt>
          <dd>{organismEntity.gbifDatasetId}</dd>
          <dt>
            <span id="gbifTaxonomicStatus">
              <Translate contentKey="amrInterpreationApp.organism.gbifTaxonomicStatus">Gbif Taxonomic Status</Translate>
            </span>
          </dt>
          <dd>{organismEntity.gbifTaxonomicStatus}</dd>
          <dt>
            <span id="kingdom">
              <Translate contentKey="amrInterpreationApp.organism.kingdom">Kingdom</Translate>
            </span>
          </dt>
          <dd>{organismEntity.kingdom}</dd>
          <dt>
            <span id="phylum">
              <Translate contentKey="amrInterpreationApp.organism.phylum">Phylum</Translate>
            </span>
          </dt>
          <dd>{organismEntity.phylum}</dd>
          <dt>
            <span id="organismClass">
              <Translate contentKey="amrInterpreationApp.organism.organismClass">Organism Class</Translate>
            </span>
          </dt>
          <dd>{organismEntity.organismClass}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="amrInterpreationApp.organism.order">Order</Translate>
            </span>
          </dt>
          <dd>{organismEntity.order}</dd>
          <dt>
            <span id="family">
              <Translate contentKey="amrInterpreationApp.organism.family">Family</Translate>
            </span>
          </dt>
          <dd>{organismEntity.family}</dd>
          <dt>
            <span id="genus">
              <Translate contentKey="amrInterpreationApp.organism.genus">Genus</Translate>
            </span>
          </dt>
          <dd>{organismEntity.genus}</dd>
        </dl>
        <Button tag={Link} to="/organism" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organism/${organismEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ organism }: IRootState) => ({
  organismEntity: organism.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrganismDetail);
