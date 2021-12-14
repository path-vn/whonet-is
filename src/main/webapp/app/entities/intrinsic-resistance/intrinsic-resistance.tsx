import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, getFilerGroup } from './intrinsic-resistance.reducer';
import { IIntrinsicResistance } from 'app/shared/model/intrinsic-resistance.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { FilterTableHeader } from 'app/shared/util/filter';

export interface IIntrinsicResistanceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const IntrinsicResistance = (props: IIntrinsicResistanceProps) => {
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

  const { intrinsicResistanceList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="intrinsic-resistance-heading" data-cy="IntrinsicResistanceHeading">
        <Translate contentKey="amrInterpreationApp.intrinsicResistance.home.title">Intrinsic Resistances</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.intrinsicResistance.home.refreshListLabel">Refresh List</Translate>
          </Button>
          {/*<Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">*/}
          {/*  <FontAwesomeIcon icon="plus" />*/}
          {/*  &nbsp;*/}
          {/*  <Translate contentKey="amrInterpreationApp.intrinsicResistance.home.createLabel">Create new Intrinsic Resistance</Translate>*/}
          {/*</Link>*/}
        </div>
      </h2>
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
        {intrinsicResistanceList && intrinsicResistanceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="amrInterpreationApp.intrinsicResistance.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'reference_table'}
                    contentKey="amrInterpreationApp.intrinsicResistance.referenceTable"
                    filterHandle={values => setSelected({ ...selected, referenceTable: values })}
                    sortHandle={() => innerSort('referenceTable')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'organism_code'}
                    contentKey="amrInterpreationApp.intrinsicResistance.organismCode"
                    filterHandle={values => setSelected({ ...selected, organismCode: values })}
                    sortHandle={() => innerSort('organismCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'organism_code_type'}
                    contentKey="amrInterpreationApp.intrinsicResistance.organismCodeType"
                    filterHandle={values => setSelected({ ...selected, organismCodeType: values })}
                    sortHandle={() => innerSort('organismCodeType')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'exception_organism_code'}
                    contentKey="amrInterpreationApp.intrinsicResistance.exceptionOrganismCode"
                    filterHandle={values => setSelected({ ...selected, exceptionOrganismCode: values })}
                    sortHandle={() => innerSort('exceptionOrganismCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'exception_organism_code_type'}
                    contentKey="amrInterpreationApp.intrinsicResistance.exceptionOrganismCodeType"
                    filterHandle={values => setSelected({ ...selected, exceptionOrganismCodeType: values })}
                    sortHandle={() => innerSort('exceptionOrganismCodeType')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'abx_code'}
                    contentKey="amrInterpreationApp.intrinsicResistance.abxCode"
                    filterHandle={values => setSelected({ ...selected, abxCode: values })}
                    sortHandle={() => innerSort('abxCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'abx_code_type'}
                    contentKey="amrInterpreationApp.intrinsicResistance.abxCodeType"
                    filterHandle={values => setSelected({ ...selected, abxCodeType: values })}
                    sortHandle={() => innerSort('abxCodeType')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'date_entered'}
                    contentKey="amrInterpreationApp.intrinsicResistance.dateEntered"
                    filterHandle={values => setSelected({ ...selected, dateEntered: values })}
                    sortHandle={() => innerSort('dateEntered')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'date_modified'}
                    contentKey="amrInterpreationApp.intrinsicResistance.dateModified"
                    filterHandle={values => setSelected({ ...selected, dateModified: values })}
                    sortHandle={() => innerSort('dateModified')}
                  />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {intrinsicResistanceList.map((intrinsicResistance, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${intrinsicResistance.id}`} color="link" size="sm">
                      {intrinsicResistance.id}
                    </Button>
                  </td>
                  <td>{intrinsicResistance.referenceTable}</td>
                  <td>{intrinsicResistance.organismCode}</td>
                  <td>{intrinsicResistance.organismCodeType}</td>
                  <td>{intrinsicResistance.exceptionOrganismCode}</td>
                  <td>{intrinsicResistance.exceptionOrganismCodeType}</td>
                  <td>{intrinsicResistance.abxCode}</td>
                  <td>{intrinsicResistance.abxCodeType}</td>
                  <td>{intrinsicResistance.dateEntered}</td>
                  <td>{intrinsicResistance.dateModified}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${intrinsicResistance.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {/*<Button*/}
                      {/*  tag={Link}*/}
                      {/*  to={`${match.url}/${intrinsicResistance.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}*/}
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
                      {/*  to={`${match.url}/${intrinsicResistance.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}*/}
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
              <Translate contentKey="amrInterpreationApp.intrinsicResistance.home.notFound">No Intrinsic Resistances found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={intrinsicResistanceList && intrinsicResistanceList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ intrinsicResistance }: IRootState) => ({
  intrinsicResistanceList: intrinsicResistance.entities,
  loading: intrinsicResistance.loading,
  totalItems: intrinsicResistance.totalItems,
  filter: intrinsicResistance.filter,
});

const mapDispatchToProps = {
  getEntities,
  getFilerGroup,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IntrinsicResistance);
