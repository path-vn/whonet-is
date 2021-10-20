import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './expert-interpretation-rules.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExpertInterpretationRulesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExpertInterpretationRulesDetail = (props: IExpertInterpretationRulesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { expertInterpretationRulesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="expertInterpretationRulesDetailsHeading">
          <Translate contentKey="amrInterpreationApp.expertInterpretationRules.detail.title">ExpertInterpretationRules</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.id}</dd>
          <dt>
            <span id="ruleCode">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.ruleCode">Rule Code</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.ruleCode}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.description">Description</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.description}</dd>
          <dt>
            <span id="organismCode">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.organismCode">Organism Code</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.organismCode}</dd>
          <dt>
            <span id="organismCodeType">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.organismCodeType">Organism Code Type</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.organismCodeType}</dd>
          <dt>
            <span id="ruleCriteria">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.ruleCriteria">Rule Criteria</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.ruleCriteria}</dd>
          <dt>
            <span id="affectedAntibiotics">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.affectedAntibiotics">Affected Antibiotics</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.affectedAntibiotics}</dd>
          <dt>
            <span id="antibioticExceptions">
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.antibioticExceptions">Antibiotic Exceptions</Translate>
            </span>
          </dt>
          <dd>{expertInterpretationRulesEntity.antibioticExceptions}</dd>
        </dl>
        <Button tag={Link} to="/expert-interpretation-rules" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/expert-interpretation-rules/${expertInterpretationRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ expertInterpretationRules }: IRootState) => ({
  expertInterpretationRulesEntity: expertInterpretationRules.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExpertInterpretationRulesDetail);
