import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './organism.reducer';
import { IOrganism } from 'app/shared/model/organism.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IOrganismProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Organism = (props: IOrganismProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { organismList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="organism-heading" data-cy="OrganismHeading">
        <Translate contentKey="amrInterpreationApp.organism.home.title">Organisms</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.organism.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="amrInterpreationApp.organism.home.createLabel">Create new Organism</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {organismList && organismList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="amrInterpreationApp.organism.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('whonetOrgCode')}>
                  <Translate contentKey="amrInterpreationApp.organism.whonetOrgCode">Whonet Org Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('organism')}>
                  <Translate contentKey="amrInterpreationApp.organism.organism">Organism</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('taxonomicStatus')}>
                  <Translate contentKey="amrInterpreationApp.organism.taxonomicStatus">Taxonomic Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('common')}>
                  <Translate contentKey="amrInterpreationApp.organism.common">Common</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('organismType')}>
                  <Translate contentKey="amrInterpreationApp.organism.organismType">Organism Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('anaerobe')}>
                  <Translate contentKey="amrInterpreationApp.organism.anaerobe">Anaerobe</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('morphology')}>
                  <Translate contentKey="amrInterpreationApp.organism.morphology">Morphology</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('subkingdomCode')}>
                  <Translate contentKey="amrInterpreationApp.organism.subkingdomCode">Subkingdom Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('familyCode')}>
                  <Translate contentKey="amrInterpreationApp.organism.familyCode">Family Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('genusGroup')}>
                  <Translate contentKey="amrInterpreationApp.organism.genusGroup">Genus Group</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('genusCode')}>
                  <Translate contentKey="amrInterpreationApp.organism.genusCode">Genus Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('speciesGroup')}>
                  <Translate contentKey="amrInterpreationApp.organism.speciesGroup">Species Group</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('serovarGroup')}>
                  <Translate contentKey="amrInterpreationApp.organism.serovarGroup">Serovar Group</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('msfGrpClin')}>
                  <Translate contentKey="amrInterpreationApp.organism.msfGrpClin">Msf Grp Clin</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sctCode')}>
                  <Translate contentKey="amrInterpreationApp.organism.sctCode">Sct Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sctText')}>
                  <Translate contentKey="amrInterpreationApp.organism.sctText">Sct Text</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dwcTaxonId')}>
                  <Translate contentKey="amrInterpreationApp.organism.dwcTaxonId">Dwc Taxon Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dwcTaxonomicStatus')}>
                  <Translate contentKey="amrInterpreationApp.organism.dwcTaxonomicStatus">Dwc Taxonomic Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gbifTaxonId')}>
                  <Translate contentKey="amrInterpreationApp.organism.gbifTaxonId">Gbif Taxon Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gbifDatasetId')}>
                  <Translate contentKey="amrInterpreationApp.organism.gbifDatasetId">Gbif Dataset Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gbifTaxonomicStatus')}>
                  <Translate contentKey="amrInterpreationApp.organism.gbifTaxonomicStatus">Gbif Taxonomic Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('kingdom')}>
                  <Translate contentKey="amrInterpreationApp.organism.kingdom">Kingdom</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phylum')}>
                  <Translate contentKey="amrInterpreationApp.organism.phylum">Phylum</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('organismClass')}>
                  <Translate contentKey="amrInterpreationApp.organism.organismClass">Organism Class</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('order')}>
                  <Translate contentKey="amrInterpreationApp.organism.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('family')}>
                  <Translate contentKey="amrInterpreationApp.organism.family">Family</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('genus')}>
                  <Translate contentKey="amrInterpreationApp.organism.genus">Genus</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {organismList.map((organism, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${organism.id}`} color="link" size="sm">
                      {organism.id}
                    </Button>
                  </td>
                  <td>{organism.whonetOrgCode}</td>
                  <td>{organism.organism}</td>
                  <td>{organism.taxonomicStatus}</td>
                  <td>{organism.common}</td>
                  <td>{organism.organismType}</td>
                  <td>{organism.anaerobe}</td>
                  <td>{organism.morphology}</td>
                  <td>{organism.subkingdomCode}</td>
                  <td>{organism.familyCode}</td>
                  <td>{organism.genusGroup}</td>
                  <td>{organism.genusCode}</td>
                  <td>{organism.speciesGroup}</td>
                  <td>{organism.serovarGroup}</td>
                  <td>{organism.msfGrpClin}</td>
                  <td>{organism.sctCode}</td>
                  <td>{organism.sctText}</td>
                  <td>{organism.dwcTaxonId}</td>
                  <td>{organism.dwcTaxonomicStatus}</td>
                  <td>{organism.gbifTaxonId}</td>
                  <td>{organism.gbifDatasetId}</td>
                  <td>{organism.gbifTaxonomicStatus}</td>
                  <td>{organism.kingdom}</td>
                  <td>{organism.phylum}</td>
                  <td>{organism.organismClass}</td>
                  <td>{organism.order}</td>
                  <td>{organism.family}</td>
                  <td>{organism.genus}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${organism.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${organism.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${organism.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="amrInterpreationApp.organism.home.notFound">No Organisms found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={organismList && organismList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ organism }: IRootState) => ({
  organismList: organism.entities,
  loading: organism.loading,
  totalItems: organism.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Organism);
