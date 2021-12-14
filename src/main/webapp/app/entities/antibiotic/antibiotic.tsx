import React, { useEffect, useRef, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import { getSortState, JhiItemCount, JhiPagination, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, getFilerGroup } from './antibiotic.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { FilterTableHeader } from 'app/shared/util/filter';

export interface IAntibioticProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Antibiotic = (props: IAntibioticProps) => {
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

  const { antibioticList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="antibiotic-heading" data-cy="AntibioticHeading">
        <Translate contentKey="amrInterpreationApp.antibiotic.home.title">Antibiotics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.antibiotic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          {/*<Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">*/}
          {/*  <FontAwesomeIcon icon="plus" />*/}
          {/*  &nbsp;*/}
          {/*  <Translate contentKey="amrInterpreationApp.antibiotic.home.createLabel">Create new Antibiotic</Translate>*/}
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
        {antibioticList && antibioticList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand">
                  <span onClick={sort('id')}>
                    <Translate contentKey="amrInterpreationApp.antibiotic.id">ID</Translate>
                  </span>
                  <FontAwesomeIcon icon="sort" onClick={sort('id')} />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'whonet_abx_code'}
                    contentKey="amrInterpreationApp.antibiotic.whonetAbxCode"
                    filterHandle={values => setSelected({ ...selected, whonetAbxCode: values })}
                    sortHandle={() => innerSort('whonetAbxCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'who_code'}
                    contentKey="amrInterpreationApp.antibiotic.whoCode"
                    filterHandle={values => setSelected({ ...selected, whoCode: values })}
                    sortHandle={() => innerSort('whoCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'din_code'}
                    contentKey="amrInterpreationApp.antibiotic.dinCode"
                    filterHandle={values => setSelected({ ...selected, dinCode: values })}
                    sortHandle={() => innerSort('dinCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'jac_code'}
                    contentKey="amrInterpreationApp.antibiotic.jacCode"
                    filterHandle={values => setSelected({ ...selected, jacCode: values })}
                    sortHandle={() => innerSort('jacCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'eucast_code'}
                    contentKey="amrInterpreationApp.antibiotic.eucastCode"
                    filterHandle={values => setSelected({ ...selected, eucastCode: values })}
                    sortHandle={() => innerSort('eucastCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'user_code'}
                    contentKey="amrInterpreationApp.antibiotic.userCode"
                    filterHandle={values => setSelected({ ...selected, userCode: values })}
                    sortHandle={() => innerSort('userCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'antibiotic'}
                    contentKey="amrInterpreationApp.antibiotic.antibiotic"
                    filterHandle={values => setSelected({ ...selected, antibiotic: values })}
                    sortHandle={() => innerSort('antibiotic')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'guidelines'}
                    contentKey="amrInterpreationApp.antibiotic.guidelines"
                    filterHandle={values => setSelected({ ...selected, guidelines: values })}
                    sortHandle={() => innerSort('guidelines')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'class'}
                    contentKey="amrInterpreationApp.antibiotic.antiboticClass"
                    filterHandle={values => setSelected({ ...selected, antiboticClass: values })}
                    sortHandle={() => innerSort('antiboticClass')}
                  />
                </th>
                <th className="hand">
                  <Translate contentKey="amrInterpreationApp.antibiotic.clsi">Clsi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'eucast'}
                    contentKey="amrInterpreationApp.antibiotic.eucast"
                    filterHandle={values => setSelected({ ...selected, eucast: values })}
                    sortHandle={() => innerSort('eucast')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'sfm'}
                    contentKey="amrInterpreationApp.antibiotic.sfm"
                    filterHandle={values => setSelected({ ...selected, sfm: values })}
                    sortHandle={() => innerSort('sfm')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'srga'}
                    contentKey="amrInterpreationApp.antibiotic.srga"
                    filterHandle={values => setSelected({ ...selected, srga: values })}
                    sortHandle={() => innerSort('srga')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'bsac'}
                    contentKey="amrInterpreationApp.antibiotic.bsac"
                    filterHandle={values => setSelected({ ...selected, bsac: values })}
                    sortHandle={() => innerSort('bsac')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'din'}
                    contentKey="amrInterpreationApp.antibiotic.din"
                    filterHandle={values => setSelected({ ...selected, din: values })}
                    sortHandle={() => innerSort('din')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'neo'}
                    contentKey="amrInterpreationApp.antibiotic.neo"
                    filterHandle={values => setSelected({ ...selected, neo: values })}
                    sortHandle={() => innerSort('neo')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'afa'}
                    contentKey="amrInterpreationApp.antibiotic.afa"
                    filterHandle={values => setSelected({ ...selected, afa: values })}
                    sortHandle={() => innerSort('afa')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'abx_number'}
                    contentKey="amrInterpreationApp.antibiotic.abxNumber"
                    filterHandle={values => setSelected({ ...selected, abxNumber: values })}
                    sortHandle={() => innerSort('abxNumber')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'potency'}
                    contentKey="amrInterpreationApp.antibiotic.potency"
                    filterHandle={values => setSelected({ ...selected, potency: values })}
                    sortHandle={() => innerSort('potency')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'atc_code'}
                    contentKey="amrInterpreationApp.antibiotic.atcCode"
                    filterHandle={values => setSelected({ ...selected, atcCode: values })}
                    sortHandle={() => innerSort('atcCode')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'prof_class'}
                    contentKey="amrInterpreationApp.antibiotic.profClass"
                    filterHandle={values => setSelected({ ...selected, profClass: values })}
                    sortHandle={() => innerSort('profClass')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'cia_category'}
                    contentKey="amrInterpreationApp.antibiotic.ciaCategory"
                    filterHandle={values => setSelected({ ...selected, ciaCategory: values })}
                    sortHandle={() => innerSort('ciaCategory')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'clsi_order'}
                    contentKey="amrInterpreationApp.antibiotic.clsiOrder"
                    filterHandle={values => setSelected({ ...selected, clsiOrder: values })}
                    sortHandle={() => innerSort('clsiOrder')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'eucast_order'}
                    contentKey="amrInterpreationApp.antibiotic.eucastOrder"
                    filterHandle={values => setSelected({ ...selected, eucastOrder: values })}
                    sortHandle={() => innerSort('eucastOrder')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'human'}
                    contentKey="amrInterpreationApp.antibiotic.human"
                    filterHandle={values => setSelected({ ...selected, human: values })}
                    sortHandle={() => innerSort('human')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'veterinary'}
                    contentKey="amrInterpreationApp.antibiotic.veterinary"
                    filterHandle={values => setSelected({ ...selected, veterinary: values })}
                    sortHandle={() => innerSort('veterinary')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'animal_gp'}
                    contentKey="amrInterpreationApp.antibiotic.animalGp"
                    filterHandle={values => setSelected({ ...selected, animalGp: values })}
                    sortHandle={() => innerSort('animalGp')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loinccomp'}
                    contentKey="amrInterpreationApp.antibiotic.loinccomp"
                    filterHandle={values => setSelected({ ...selected, loinccomp: values })}
                    sortHandle={() => innerSort('loinccomp')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincgen'}
                    contentKey="amrInterpreationApp.antibiotic.loincgen"
                    filterHandle={values => setSelected({ ...selected, loincgen: values })}
                    sortHandle={() => innerSort('loincgen')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincdisk'}
                    contentKey="amrInterpreationApp.antibiotic.loincdisk"
                    filterHandle={values => setSelected({ ...selected, loincdisk: values })}
                    sortHandle={() => innerSort('loincdisk')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincmic'}
                    contentKey="amrInterpreationApp.antibiotic.loincmic"
                    filterHandle={values => setSelected({ ...selected, loincmic: values })}
                    sortHandle={() => innerSort('loincmic')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincetest'}
                    contentKey="amrInterpreationApp.antibiotic.loincetest"
                    filterHandle={values => setSelected({ ...selected, loincetest: values })}
                    sortHandle={() => innerSort('loincetest')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincslow'}
                    contentKey="amrInterpreationApp.antibiotic.loincslow"
                    filterHandle={values => setSelected({ ...selected, loincslow: values })}
                    sortHandle={() => innerSort('loincslow')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincafb'}
                    contentKey="amrInterpreationApp.antibiotic.loincafb"
                    filterHandle={values => setSelected({ ...selected, loincafb: values })}
                    sortHandle={() => innerSort('loincafb')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincsbt'}
                    contentKey="amrInterpreationApp.antibiotic.loincsbt"
                    filterHandle={values => setSelected({ ...selected, loincsbt: values })}
                    sortHandle={() => innerSort('loincsbt')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'loincmlc'}
                    contentKey="amrInterpreationApp.antibiotic.loincmlc"
                    filterHandle={values => setSelected({ ...selected, loincmlc: values })}
                    sortHandle={() => innerSort('loincmlc')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'date_entered'}
                    contentKey="amrInterpreationApp.antibiotic.dateEntered"
                    filterHandle={values => setSelected({ ...selected, dateEntered: values })}
                    sortHandle={() => innerSort('dateEntered')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'date_modified'}
                    contentKey="amrInterpreationApp.antibiotic.dateModified"
                    filterHandle={values => setSelected({ ...selected, dateModified: values })}
                    sortHandle={() => innerSort('dateModified')}
                  />
                </th>
                <th className="hand">
                  <FilterTableHeader
                    filter={props.filter}
                    handle={props.getFilerGroup}
                    name={'comments'}
                    contentKey="amrInterpreationApp.antibiotic.comments"
                    filterHandle={values => setSelected({ ...selected, comments: values })}
                    sortHandle={() => innerSort('comments')}
                  />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {antibioticList.map((antibiotic, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${antibiotic.id}`} color="link" size="sm">
                      {antibiotic.id}
                    </Button>
                  </td>
                  <td>{antibiotic.whonetAbxCode}</td>
                  <td>{antibiotic.whoCode}</td>
                  <td>{antibiotic.dinCode}</td>
                  <td>{antibiotic.jacCode}</td>
                  <td>{antibiotic.eucastCode}</td>
                  <td>{antibiotic.userCode}</td>
                  <td>{antibiotic.antibiotic}</td>
                  <td>{antibiotic.guidelines}</td>
                  <td>{antibiotic.antiboticClass}</td>
                  <td>{antibiotic.clsi}</td>
                  <td>{antibiotic.eucast}</td>
                  <td>{antibiotic.sfm}</td>
                  <td>{antibiotic.srga}</td>
                  <td>{antibiotic.bsac}</td>
                  <td>{antibiotic.din}</td>
                  <td>{antibiotic.neo}</td>
                  <td>{antibiotic.afa}</td>
                  <td>{antibiotic.abxNumber}</td>
                  <td>{antibiotic.potency}</td>
                  <td>{antibiotic.atcCode}</td>
                  <td>{antibiotic.profClass}</td>
                  <td>{antibiotic.ciaCategory}</td>
                  <td>{antibiotic.clsiOrder}</td>
                  <td>{antibiotic.eucastOrder}</td>
                  <td>{antibiotic.human}</td>
                  <td>{antibiotic.veterinary}</td>
                  <td>{antibiotic.animalGp}</td>
                  <td>{antibiotic.loinccomp}</td>
                  <td>{antibiotic.loincgen}</td>
                  <td>{antibiotic.loincdisk}</td>
                  <td>{antibiotic.loincmic}</td>
                  <td>{antibiotic.loincetest}</td>
                  <td>{antibiotic.loincslow}</td>
                  <td>{antibiotic.loincafb}</td>
                  <td>{antibiotic.loincsbt}</td>
                  <td>{antibiotic.loincmlc}</td>
                  <td>{antibiotic.dateEntered}</td>
                  <td>{antibiotic.dateModified}</td>
                  <td>{antibiotic.comments}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${antibiotic.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {/*<Button*/}
                      {/*  tag={Link}*/}
                      {/*  to={`${match.url}/${antibiotic.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}*/}
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
                      {/*  to={`${match.url}/${antibiotic.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}*/}
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
              <Translate contentKey="amrInterpreationApp.antibiotic.home.notFound">No Antibiotics found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={antibioticList && antibioticList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ antibiotic }: IRootState) => ({
  antibioticList: antibiotic.entities,
  loading: antibiotic.loading,
  totalItems: antibiotic.totalItems,
  filter: antibiotic.filter,
});

const mapDispatchToProps = {
  getEntities,
  getFilerGroup,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Antibiotic);
