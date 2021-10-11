import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
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
            <span id="path">
              <Translate contentKey="amrInterpreationApp.breakpoint.path">Path</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.path}</dd>
          <dt>
            <span id="query">
              <Translate contentKey="amrInterpreationApp.breakpoint.query">Query</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.query}</dd>
          <dt>
            <span id="antibioticQuery">
              <Translate contentKey="amrInterpreationApp.breakpoint.antibioticQuery">Antibiotic Query</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.antibioticQuery}</dd>
          <dt>
            <span id="organismQuery">
              <Translate contentKey="amrInterpreationApp.breakpoint.organismQuery">Organism Query</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.organismQuery}</dd>
          <dt>
            <span id="intrinsicResistanceQuery">
              <Translate contentKey="amrInterpreationApp.breakpoint.intrinsicResistanceQuery">Intrinsic Resistance Query</Translate>
            </span>
          </dt>
          <dd>{breakpointEntity.intrinsicResistanceQuery}</dd>
          <dt>
            <span id="binaryData">
              <Translate contentKey="amrInterpreationApp.breakpoint.binaryData">Binary Data</Translate>
            </span>
          </dt>
          <dd>
            {breakpointEntity.binaryData ? (
              <div>
                {breakpointEntity.binaryDataContentType ? (
                  <a onClick={openFile(breakpointEntity.binaryDataContentType, breakpointEntity.binaryData)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {breakpointEntity.binaryDataContentType}, {byteSize(breakpointEntity.binaryData)}
                </span>
              </div>
            ) : null}
          </dd>
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
