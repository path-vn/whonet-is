import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './breakpoint.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBreakpointDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BreakpointDetail = (props: IBreakpointDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { breakpointEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="breakpointDetailsHeading">
          <Translate contentKey="amrInterpreationApp.breakpoint.detail.title">Breakpoint</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.id}</dd>
          <dt>
            <span id="guidelines">
              <Translate contentKey="amrInterpreationApp.breakpoint.guidelines">Guidelines</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.guidelines}</dd>
          <dt>
            <span id="year">
              <Translate contentKey="amrInterpreationApp.breakpoint.year">Year</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.year}</dd>
          <dt>
            <span id="testMethod">
              <Translate contentKey="amrInterpreationApp.breakpoint.testMethod">Test Method</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.testMethod}</dd>
          <dt>
            <span id="potency">
              <Translate contentKey="amrInterpreationApp.breakpoint.potency">Potency</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.potency}</dd>
          <dt>
            <span id="organismCode">
              <Translate contentKey="amrInterpreationApp.breakpoint.organismCode">Organism Code</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.organismCode}</dd>
          <dt>
            <span id="organismCodeType">
              <Translate contentKey="amrInterpreationApp.breakpoint.organismCodeType">Organism Code Type</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.organismCodeType}</dd>
          <dt>
            <span id="breakpointType">
              <Translate contentKey="amrInterpreationApp.breakpoint.breakpointType">Breakpoint Type</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.breakpointType}</dd>
          <dt>
            <span id="host">
              <Translate contentKey="amrInterpreationApp.breakpoint.host">Host</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.host}</dd>
          <dt>
            <span id="siteOfInfection">
              <Translate contentKey="amrInterpreationApp.breakpoint.siteOfInfection">Site Of Infection</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.siteOfInfection}</dd>
          <dt>
            <span id="referenceTable">
              <Translate contentKey="amrInterpreationApp.breakpoint.referenceTable">Reference Table</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.referenceTable}</dd>
          <dt>
            <span id="referenceSequence">
              <Translate contentKey="amrInterpreationApp.breakpoint.referenceSequence">Reference Sequence</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.referenceSequence}</dd>
          <dt>
            <span id="whonetAbxCode">
              <Translate contentKey="amrInterpreationApp.breakpoint.whonetAbxCode">Whonet Abx Code</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.whonetAbxCode}</dd>
          <dt>
            <span id="whonetTest">
              <Translate contentKey="amrInterpreationApp.breakpoint.whonetTest">Whonet Test</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.whonetTest}</dd>
          <dt>
            <span id="r">
              <Translate contentKey="amrInterpreationApp.breakpoint.r">R</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.r}</dd>
          <dt>
            <span id="i">
              <Translate contentKey="amrInterpreationApp.breakpoint.i">I</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.i}</dd>
          <dt>
            <span id="sdd">
              <Translate contentKey="amrInterpreationApp.breakpoint.sdd">Sdd</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.sdd}</dd>
          <dt>
            <span id="s">
              <Translate contentKey="amrInterpreationApp.breakpoint.s">S</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.s}</dd>
          <dt>
            <span id="ecvEcoff">
              <Translate contentKey="amrInterpreationApp.breakpoint.ecvEcoff">Ecv Ecoff</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.ecvEcoff}</dd>
          <dt>
            <span id="dateEntered">
              <Translate contentKey="amrInterpreationApp.breakpoint.dateEntered">Date Entered</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.dateEntered}</dd>
          <dt>
            <span id="dateModified">
              <Translate contentKey="amrInterpreationApp.breakpoint.dateModified">Date Modified</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.dateModified}</dd>
          <dt>
            <span id="comments">
              <Translate contentKey="amrInterpreationApp.breakpoint.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.comments}</dd>
        </dl>
        <Button tag={Link} to="/breakpoint" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/breakpoint/${breakpointEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ breakpoint }: IRootState) => ({
  breakpointEntity: breakpoint.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BreakpointDetail);
