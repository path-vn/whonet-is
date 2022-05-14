import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, getFilerGroup } from './organism.reducer';
import { IOrganism } from 'app/shared/model/organism.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { empty, overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { FilterTableHeader } from 'app/shared/util/filter';

export interface IOrganismProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Organism = (props: IOrganismProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [selected, setSelected] = useState({});

  const getAllEntities = () => {
    props.getEntities(
      paginationState.activePage - 1,
      paginationState.itemsPerPage,
      `${paginationState.sort},${paginationState.order}`,
      selected
    );
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
  }, [paginationState.activePage, paginationState.order, paginationState.sort, selected]);

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

  const innerSort = p => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };
  const clearFilter = () => {
    setSelected({});
  };

  const { organismList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="organism-heading" data-cy="OrganismHeading">
        <Translate contentKey="amrInterpreationApp.organism.home.title">Organisms</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info">
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
      {empty(selected) || (Object.keys(selected).length === 0 && <span>Click on column name to filter</span>)}
      {selected &&
        Object.keys(selected).map((k, i) => {
          return (
            <div key={`master-div-${i}`} style={{ display: 'inline' }}>
              <span key={`master-${i}`} id={`id-master-${i}`} className="badge badge-secondary">
                {' '}
                {k}:{' '}
              </span>
              {selected[k]
                .map(s => s.value)
                .map((s, index) => {
                  return (
                    <span key={`selected-${i}-${index}`} id={`id-selected-${i}-${index}`} className="badge badge-info">
                      {s}
                    </span>
                  );
                })}
            </div>
          );
        })}
      {selected && Object.keys(selected).length > 0 && (
        <span onClick={clearFilter} className="badge badge-danger">
          X
        </span>
      )}
      <div className="table-responsive">
        {organismList && organismList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand">
                  <span onClick={sort('id')}>
                    <Translate contentKey="amrInterpreationApp.organism.id">ID</Translate>
                  </span>
                  <FontAwesomeIcon icon="sort" onClick={sort('id')} />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'whonet_org_code'}
                    contentKey="amrInterpreationApp.organism.whonetOrgCode"
                    filterHandle={values => setSelected({ ...selected, whonetOrgCode: values })}
                    sortHandle={() => innerSort('whonetOrgCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'organism'}
                    contentKey="amrInterpreationApp.organism.organism"
                    filterHandle={values => setSelected({ ...selected, organism: values })}
                    sortHandle={() => innerSort('organism')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'taxonomic_status'}
                    contentKey="amrInterpreationApp.organism.taxonomicStatus"
                    filterHandle={values => setSelected({ ...selected, taxonomicStatus: values })}
                    sortHandle={() => innerSort('taxonomicStatus')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'common'}
                    contentKey="amrInterpreationApp.organism.common"
                    filterHandle={values => setSelected({ ...selected, common: values })}
                    sortHandle={() => innerSort('common')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'organism_type'}
                    contentKey="amrInterpreationApp.organism.organismType"
                    filterHandle={values => setSelected({ ...selected, organismType: values })}
                    sortHandle={() => innerSort('organismType')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'anaerobe'}
                    contentKey="amrInterpreationApp.organism.anaerobe"
                    filterHandle={values => setSelected({ ...selected, anaerobe: values })}
                    sortHandle={() => innerSort('anaerobe')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'morphology'}
                    contentKey="amrInterpreationApp.organism.morphology"
                    filterHandle={values => setSelected({ ...selected, morphology: values })}
                    sortHandle={() => innerSort('morphology')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'subkingdom_code'}
                    contentKey="amrInterpreationApp.organism.subkingdomCode"
                    filterHandle={values => setSelected({ ...selected, subkingdomCode: values })}
                    sortHandle={() => innerSort('subkingdomCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'family_code'}
                    contentKey="amrInterpreationApp.organism.familyCode"
                    filterHandle={values => setSelected({ ...selected, familyCode: values })}
                    sortHandle={() => innerSort('familyCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'genus_group'}
                    contentKey="amrInterpreationApp.organism.genusGroup"
                    filterHandle={values => setSelected({ ...selected, genusGroup: values })}
                    sortHandle={() => innerSort('genusGroup')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'genus_code'}
                    contentKey="amrInterpreationApp.organism.genusCode"
                    filterHandle={values => setSelected({ ...selected, genusCode: values })}
                    sortHandle={() => innerSort('genusCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'species_group'}
                    contentKey="amrInterpreationApp.organism.speciesGroup"
                    filterHandle={values => setSelected({ ...selected, speciesGroup: values })}
                    sortHandle={() => innerSort('speciesGroup')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'serovar_group'}
                    contentKey="amrInterpreationApp.organism.serovarGroup"
                    filterHandle={values => setSelected({ ...selected, serovarGroup: values })}
                    sortHandle={() => innerSort('serovarGroup')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'msf_grp_clin'}
                    contentKey="amrInterpreationApp.organism.msfGrpClin"
                    filterHandle={values => setSelected({ ...selected, msfGrpClin: values })}
                    sortHandle={() => innerSort('msfGrpClin')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'sct_code'}
                    contentKey="amrInterpreationApp.organism.sctCode"
                    filterHandle={values => setSelected({ ...selected, sctCode: values })}
                    sortHandle={() => innerSort('sctCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'sct_text'}
                    contentKey="amrInterpreationApp.organism.sctText"
                    filterHandle={values => setSelected({ ...selected, sctText: values })}
                    sortHandle={() => innerSort('sctText')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'dwc_taxon_id'}
                    contentKey="amrInterpreationApp.organism.dwcTaxonId"
                    filterHandle={values => setSelected({ ...selected, dwcTaxonId: values })}
                    sortHandle={() => innerSort('dwcTaxonId')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'dwc_taxonomic_status'}
                    contentKey="amrInterpreationApp.organism.dwcTaxonomicStatus"
                    filterHandle={values => setSelected({ ...selected, dwcTaxonomicStatus: values })}
                    sortHandle={() => innerSort('dwcTaxonomicStatus')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'gbif_taxon_id'}
                    contentKey="amrInterpreationApp.organism.gbifTaxonId"
                    filterHandle={values => setSelected({ ...selected, gbifTaxonId: values })}
                    sortHandle={() => innerSort('gbifTaxonId')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'gbif_dataset_id'}
                    contentKey="amrInterpreationApp.organism.gbifDatasetId"
                    filterHandle={values => setSelected({ ...selected, gbifDatasetId: values })}
                    sortHandle={() => innerSort('gbifDatasetId')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'gbif_taxonomic_status'}
                    contentKey="amrInterpreationApp.organism.gbifTaxonomicStatus"
                    filterHandle={values => setSelected({ ...selected, gbifTaxonomicStatus: values })}
                    sortHandle={() => innerSort('gbifTaxonomicStatus')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'kingdom'}
                    contentKey="amrInterpreationApp.organism.kingdom"
                    filterHandle={values => setSelected({ ...selected, kingdom: values })}
                    sortHandle={() => innerSort('kingdom')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'phylum'}
                    contentKey="amrInterpreationApp.organism.phylum"
                    filterHandle={values => setSelected({ ...selected, phylum: values })}
                    sortHandle={() => innerSort('phylum')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'class'}
                    contentKey="amrInterpreationApp.organism.organismClass"
                    filterHandle={values => setSelected({ ...selected, organismClass: values })}
                    sortHandle={() => innerSort('organismClass')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'family'}
                    contentKey="amrInterpreationApp.organism.family"
                    filterHandle={values => setSelected({ ...selected, family: values })}
                    sortHandle={() => innerSort('family')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'genus'}
                    contentKey="amrInterpreationApp.organism.genus"
                    filterHandle={values => setSelected({ ...selected, genus: values })}
                    sortHandle={() => innerSort('genus')}
                  />
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
  filter: organism.filter,
});

const mapDispatchToProps = {
  getEntities,
  getFilerGroup,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Organism);
