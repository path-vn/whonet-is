import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './intrinsic-resistance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIntrinsicResistanceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IntrinsicResistanceDetail = (props: IIntrinsicResistanceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { intrinsicResistanceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="intrinsicResistanceDetailsHeading">
          <Translate contentKey="amrInterpreationApp.intrinsicResistance.detail.title">IntrinsicResistance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.id}</dd>
          <dt>
            <span id="referenceTable">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.referenceTable">Reference Table</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.referenceTable}</dd>
          <dt>
            <span id="organismCode">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.organismCode">Organism Code</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.organismCode}</dd>
          <dt>
            <span id="organismCodeType">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.organismCodeType">Organism Code Type</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.organismCodeType}</dd>
          <dt>
            <span id="exceptionOrganismCode">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.exceptionOrganismCode">Exception Organism Code</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.exceptionOrganismCode}</dd>
          <dt>
            <span id="exceptionOrganismCodeType">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.exceptionOrganismCodeType">
                Exception Organism Code Type
              </Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.exceptionOrganismCodeType}</dd>
          <dt>
            <span id="abxCode">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.abxCode">Abx Code</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.abxCode}</dd>
          <dt>
            <span id="abxCodeType">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.abxCodeType">Abx Code Type</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.abxCodeType}</dd>
          <dt>
            <span id="dateEntered">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.dateEntered">Date Entered</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.dateEntered}</dd>
          <dt>
            <span id="dateModified">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.dateModified">Date Modified</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.dateModified}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{intrinsicResistanceEntity.comments}</dd>
        </dl>
        <Button tag={Link} to="/intrinsic-resistance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/intrinsic-resistance/${intrinsicResistanceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ intrinsicResistance }: IRootState) => ({
  intrinsicResistanceEntity: intrinsicResistance.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IntrinsicResistanceDetail);
