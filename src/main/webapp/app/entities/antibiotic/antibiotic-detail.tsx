import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './antibiotic.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAntibioticDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AntibioticDetail = (props: IAntibioticDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { antibioticEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="antibioticDetailsHeading">
          <Translate contentKey="amrInterpreationApp.antibiotic.detail.title">Antibiotic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.id}</dd>
          <dt>
            <span id="whonetAbxCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.whonetAbxCode">Whonet Abx Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.whonetAbxCode}</dd>
          <dt>
            <span id="whoCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.whoCode">Who Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.whoCode}</dd>
          <dt>
            <span id="dinCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.dinCode">Din Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.dinCode}</dd>
          <dt>
            <span id="jacCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.jacCode">Jac Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.jacCode}</dd>
          <dt>
            <span id="eucastCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.eucastCode">Eucast Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.eucastCode}</dd>
          <dt>
            <span id="userCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.userCode">User Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.userCode}</dd>
          <dt>
            <span id="antibiotic">
              <Translate contentKey="amrInterpreationApp.antibiotic.antibiotic">Antibiotic</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.antibiotic}</dd>
          <dt>
            <span id="guidelines">
              <Translate contentKey="amrInterpreationApp.antibiotic.guidelines">Guidelines</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.guidelines}</dd>
          <dt>
            <span id="clsi">
              <Translate contentKey="amrInterpreationApp.antibiotic.clsi">Clsi</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.clsi}</dd>
          <dt>
            <span id="eucast">
              <Translate contentKey="amrInterpreationApp.antibiotic.eucast">Eucast</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.eucast}</dd>
          <dt>
            <span id="sfm">
              <Translate contentKey="amrInterpreationApp.antibiotic.sfm">Sfm</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.sfm}</dd>
          <dt>
            <span id="srga">
              <Translate contentKey="amrInterpreationApp.antibiotic.srga">Srga</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.srga}</dd>
          <dt>
            <span id="bsac">
              <Translate contentKey="amrInterpreationApp.antibiotic.bsac">Bsac</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.bsac}</dd>
          <dt>
            <span id="din">
              <Translate contentKey="amrInterpreationApp.antibiotic.din">Din</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.din}</dd>
          <dt>
            <span id="neo">
              <Translate contentKey="amrInterpreationApp.antibiotic.neo">Neo</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.neo}</dd>
          <dt>
            <span id="afa">
              <Translate contentKey="amrInterpreationApp.antibiotic.afa">Afa</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.afa}</dd>
          <dt>
            <span id="abxNumber">
              <Translate contentKey="amrInterpreationApp.antibiotic.abxNumber">Abx Number</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.abxNumber}</dd>
          <dt>
            <span id="potency">
              <Translate contentKey="amrInterpreationApp.antibiotic.potency">Potency</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.potency}</dd>
          <dt>
            <span id="atcCode">
              <Translate contentKey="amrInterpreationApp.antibiotic.atcCode">Atc Code</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.atcCode}</dd>
          <dt>
            <span id="profClass">
              <Translate contentKey="amrInterpreationApp.antibiotic.profClass">Prof Class</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.profClass}</dd>
          <dt>
            <span id="ciaCategory">
              <Translate contentKey="amrInterpreationApp.antibiotic.ciaCategory">Cia Category</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.ciaCategory}</dd>
          <dt>
            <span id="clsiOrder">
              <Translate contentKey="amrInterpreationApp.antibiotic.clsiOrder">Clsi Order</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.clsiOrder}</dd>
          <dt>
            <span id="eucastOrder">
              <Translate contentKey="amrInterpreationApp.antibiotic.eucastOrder">Eucast Order</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.eucastOrder}</dd>
          <dt>
            <span id="human">
              <Translate contentKey="amrInterpreationApp.antibiotic.human">Human</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.human}</dd>
          <dt>
            <span id="veterinary">
              <Translate contentKey="amrInterpreationApp.antibiotic.veterinary">Veterinary</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.veterinary}</dd>
          <dt>
            <span id="animalGp">
              <Translate contentKey="amrInterpreationApp.antibiotic.animalGp">Animal Gp</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.animalGp}</dd>
          <dt>
            <span id="loinccomp">
              <Translate contentKey="amrInterpreationApp.antibiotic.loinccomp">Loinccomp</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loinccomp}</dd>
          <dt>
            <span id="loincgen">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincgen">Loincgen</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincgen}</dd>
          <dt>
            <span id="loincdisk">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincdisk">Loincdisk</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincdisk}</dd>
          <dt>
            <span id="loincmic">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincmic">Loincmic</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincmic}</dd>
          <dt>
            <span id="loincetest">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincetest">Loincetest</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincetest}</dd>
          <dt>
            <span id="loincslow">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincslow">Loincslow</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincslow}</dd>
          <dt>
            <span id="loincafb">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincafb">Loincafb</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincafb}</dd>
          <dt>
            <span id="loincsbt">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincsbt">Loincsbt</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincsbt}</dd>
          <dt>
            <span id="loincmlc">
              <Translate contentKey="amrInterpreationApp.antibiotic.loincmlc">Loincmlc</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.loincmlc}</dd>
          <dt>
            <span id="dateEntered">
              <Translate contentKey="amrInterpreationApp.antibiotic.dateEntered">Date Entered</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.dateEntered}</dd>
          <dt>
            <span id="dateModified">
              <Translate contentKey="amrInterpreationApp.antibiotic.dateModified">Date Modified</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.dateModified}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="amrInterpreationApp.antibiotic.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{antibioticEntity.comments}</dd>
        </dl>
        <Button tag={Link} to="/antibiotic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/antibiotic/${antibioticEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ antibiotic }: IRootState) => ({
  antibioticEntity: antibiotic.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AntibioticDetail);
