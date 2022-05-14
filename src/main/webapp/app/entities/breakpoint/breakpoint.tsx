import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import { getSortState, JhiItemCount, JhiPagination, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, getFilerGroup } from './breakpoint.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { empty, overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { FilterTableHeader } from 'app/shared/util/filter';

export interface IBreakpointProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Breakpoint = (props: IBreakpointProps) => {
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
    innerSort(p);
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
  const { breakpointList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="breakpoint-heading" data-cy="BreakpointHeading">
        <Translate contentKey="amrInterpreationApp.breakpoint.home.title">Breakpoints</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.breakpoint.home.refreshListLabel">Refresh List</Translate>
          </Button>
          {/*<Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">*/}
          {/*  <FontAwesomeIcon icon="plus" />*/}
          {/*  &nbsp;*/}
          {/*  <Translate contentKey="amrInterpreationApp.breakpoint.home.createLabel">Create new Breakpoint</Translate>*/}
          {/*</Link>*/}
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
        {breakpointList && breakpointList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="amrInterpreationApp.breakpoint.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'guidelines'}
                    contentKey="amrInterpreationApp.breakpoint.guidelines"
                    filterHandle={values => setSelected({ ...selected, guidelines: values })}
                    sortHandle={() => innerSort('guidelines')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'year'}
                    contentKey="amrInterpreationApp.breakpoint.year"
                    filterHandle={values => setSelected({ ...selected, year: values })}
                    sortHandle={() => innerSort('year')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'test_method'}
                    contentKey="amrInterpreationApp.breakpoint.testMethod"
                    filterHandle={values => setSelected({ ...selected, testMethod: values })}
                    sortHandle={() => innerSort('testMethod')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'potency'}
                    contentKey="amrInterpreationApp.breakpoint.potency"
                    filterHandle={values => setSelected({ ...selected, potency: values })}
                    sortHandle={() => innerSort('potency')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'organism_code'}
                    contentKey="amrInterpreationApp.breakpoint.organismCode"
                    filterHandle={values => setSelected({ ...selected, organismCode: values })}
                    sortHandle={() => innerSort('organismCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'organism_code_type'}
                    contentKey="amrInterpreationApp.breakpoint.organismCodeType"
                    filterHandle={values => setSelected({ ...selected, organismCodeType: values })}
                    sortHandle={() => innerSort('organismCodeType')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'breakpoint_type'}
                    contentKey="amrInterpreationApp.breakpoint.breakpointType"
                    filterHandle={values => setSelected({ ...selected, breakpointType: values })}
                    sortHandle={() => innerSort('breakpointType')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'host'}
                    contentKey="amrInterpreationApp.breakpoint.host"
                    filterHandle={values => setSelected({ ...selected, host: values })}
                    sortHandle={() => innerSort('host')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'site_of_infection'}
                    contentKey="amrInterpreationApp.breakpoint.siteOfInfection"
                    filterHandle={values => setSelected({ ...selected, siteOfInfection: values })}
                    sortHandle={() => innerSort('siteOfInfection')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'reference_table'}
                    contentKey="amrInterpreationApp.breakpoint.referenceTable"
                    filterHandle={values => setSelected({ ...selected, referenceTable: values })}
                    sortHandle={() => innerSort('referenceTable')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'reference_sequence'}
                    contentKey="amrInterpreationApp.breakpoint.referenceSequence"
                    filterHandle={values => setSelected({ ...selected, referenceSequence: values })}
                    sortHandle={() => innerSort('referenceSequence')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'whonet_abx_code'}
                    contentKey="amrInterpreationApp.breakpoint.whonetAbxCode"
                    filterHandle={values => setSelected({ ...selected, whonetAbxCode: values })}
                    sortHandle={() => innerSort('whonetAbxCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'whonet_test'}
                    contentKey="amrInterpreationApp.breakpoint.whonetTest"
                    filterHandle={values => setSelected({ ...selected, whonetTest: values })}
                    sortHandle={() => innerSort('whonetTest')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'r'}
                    contentKey="amrInterpreationApp.breakpoint.r"
                    filterHandle={values => setSelected({ ...selected, r: values })}
                    sortHandle={() => innerSort('r')}
                  />{' '}
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'i'}
                    contentKey="amrInterpreationApp.breakpoint.i"
                    filterHandle={values => setSelected({ ...selected, i: values })}
                    sortHandle={() => innerSort('i')}
                  />{' '}
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'sdd'}
                    contentKey="amrInterpreationApp.breakpoint.sdd"
                    filterHandle={values => setSelected({ ...selected, sdd: values })}
                    sortHandle={() => innerSort('sdd')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'s'}
                    contentKey="amrInterpreationApp.breakpoint.s"
                    filterHandle={values => setSelected({ ...selected, s: values })}
                    sortHandle={() => innerSort('s')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'ecv_ecoff'}
                    contentKey="amrInterpreationApp.breakpoint.ecvEcoff"
                    filterHandle={values => setSelected({ ...selected, ecvEcoff: values })}
                    sortHandle={() => innerSort('ecvEcoff')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'date_entered'}
                    contentKey="amrInterpreationApp.breakpoint.dateEntered"
                    filterHandle={values => setSelected({ ...selected, dateEntered: values })}
                    sortHandle={() => innerSort('dateEntered')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'date_modified'}
                    contentKey="amrInterpreationApp.breakpoint.dateModified"
                    filterHandle={values => setSelected({ ...selected, dateModified: values })}
                    sortHandle={() => innerSort('dateModified')}
                  />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {breakpointList.map((breakpoint, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${breakpoint.id}`} color="link" size="sm">
                      {breakpoint.id}
                    </Button>
                  </td>
                  <td>{breakpoint.guidelines}</td>
                  <td>{breakpoint.year}</td>
                  <td>{breakpoint.testMethod}</td>
                  <td>{breakpoint.potency}</td>
                  <td>{breakpoint.organismCode}</td>
                  <td>{breakpoint.organismCodeType}</td>
                  <td>{breakpoint.breakpointType}</td>
                  <td>{breakpoint.host}</td>
                  <td>{breakpoint.siteOfInfection}</td>
                  <td>{breakpoint.referenceTable}</td>
                  <td>{breakpoint.referenceSequence}</td>
                  <td>{breakpoint.whonetAbxCode}</td>
                  <td>{breakpoint.whonetTest}</td>
                  <td>{breakpoint.r}</td>
                  <td>{breakpoint.i}</td>
                  <td>{breakpoint.sdd}</td>
                  <td>{breakpoint.s}</td>
                  <td>{breakpoint.ecvEcoff}</td>
                  <td>{breakpoint.dateEntered}</td>
                  <td>{breakpoint.dateModified}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${breakpoint.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {/*<Button*/}
                      {/*  tag={Link}*/}
                      {/*  to={`${match.url}/${breakpoint.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}*/}
                      {/*  color="primary"*/}
                      {/*  size="sm"*/}
                      {/*  data-cy="entityEditButton"*/}
                      {/*>*/}
                      {/*  <FontAwesomeIcon icon="pencil-alt" />{' '}*/}
                      {/*  <span className="d-none d-md-inline">*/}
                      {/*    <Translate contentKey="entity.action.edit">Edit</Translate>*/}
                      {/*  </span>*/}
                      {/*</Button>*/}
                      {/*<Button*/}
                      {/*  tag={Link}*/}
                      {/*  to={`${match.url}/${breakpoint.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}*/}
                      {/*  color="danger"*/}
                      {/*  size="sm"*/}
                      {/*  data-cy="entityDeleteButton"*/}
                      {/*>*/}
                      {/*  <FontAwesomeIcon icon="trash" />{' '}*/}
                      {/*  <span className="d-none d-md-inline">*/}
                      {/*    <Translate contentKey="entity.action.delete">Delete</Translate>*/}
                      {/*  </span>*/}
                      {/*</Button>*/}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="amrInterpreationApp.breakpoint.home.notFound">No Breakpoints found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={breakpointList && breakpointList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ breakpoint }: IRootState) => ({
  breakpointList: breakpoint.entities,
  loading: breakpoint.loading,
  totalItems: breakpoint.totalItems,
  filter: breakpoint.filter,
});

const mapDispatchToProps = {
  getEntities,
  getFilerGroup,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Breakpoint);
