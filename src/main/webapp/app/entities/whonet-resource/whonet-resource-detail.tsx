import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './whonet-resource.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWhonetResourceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WhonetResourceDetail = (props: IWhonetResourceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { whonetResourceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="whonetResourceDetailsHeading">
          <Translate contentKey="amrInterpreationApp.whonetResource.detail.title">WhonetResource</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.id}</dd>
          <dt>
            <span id="uploadDate">
              <Translate contentKey="amrInterpreationApp.whonetResource.uploadDate">Upload Date</Translate>
            </span>
          </dt>
          <dd>
            {whonetResourceEntity.uploadDate ? (
              <TextFormat value={whonetResourceEntity.uploadDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="antibiotic">
              <Translate contentKey="amrInterpreationApp.whonetResource.antibiotic">Antibiotic</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.antibiotic}</dd>
          <dt>
            <span id="organism">
              <Translate contentKey="amrInterpreationApp.whonetResource.organism">Organism</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.organism}</dd>
          <dt>
            <span id="intrinsicResistance">
              <Translate contentKey="amrInterpreationApp.whonetResource.intrinsicResistance">Intrinsic Resistance</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.intrinsicResistance}</dd>
          <dt>
            <span id="expertRule">
              <Translate contentKey="amrInterpreationApp.whonetResource.expertRule">Expert Rule</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.expertRule}</dd>
          <dt>
            <span id="breakPoint">
              <Translate contentKey="amrInterpreationApp.whonetResource.breakPoint">Break Point</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.breakPoint}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="amrInterpreationApp.whonetResource.status">Status</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.status}</dd>
          <dt>
            <span id="importedDate">
              <Translate contentKey="amrInterpreationApp.whonetResource.importedDate">Imported Date</Translate>
            </span>
          </dt>
          <dd>
            {whonetResourceEntity.importedDate ? (
              <TextFormat value={whonetResourceEntity.importedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="message">
              <Translate contentKey="amrInterpreationApp.whonetResource.message">Message</Translate>
            </span>
          </dt>
          <dd>{whonetResourceEntity.message}</dd>
        </dl>
        <Button tag={Link} to="/whonet-resource" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/whonet-resource/${whonetResourceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ whonetResource }: IRootState) => ({
  whonetResourceEntity: whonetResource.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WhonetResourceDetail);
