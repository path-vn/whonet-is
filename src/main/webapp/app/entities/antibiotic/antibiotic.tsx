import React, { useEffect, useRef, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import { getSortState, JhiItemCount, JhiPagination, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './antibiotic.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { empty, overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import FilterPopup from 'app/shared/layout/filter/filter';

export interface IAntibioticProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Antibiotic = (props: IAntibioticProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const filterRef = useRef();

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
  // const openTooltip = () => {
  //   console.log(filterRef, filterRef.current)
  //   if (!empty(filterRef) && !empty(filterRef.current)) {
  //     filterRef.current.open();
  //   }
  // };

  const filter = p => () => {
    // openTooltip();
  };

  const { antibioticList, match, loading, totalItems } = props;
  return (
    <div>
      <FilterPopup ref={filterRef} />
      <h2 id="antibiotic-heading" data-cy="AntibioticHeading">
        <Translate contentKey="amrInterpreationApp.antibiotic.home.title">Antibiotics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.antibiotic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="amrInterpreationApp.antibiotic.home.createLabel">Create new Antibiotic</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {antibioticList && antibioticList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand">
                  <span onClick={filter('id')}>
                    <Translate contentKey="amrInterpreationApp.antibiotic.id">ID</Translate>
                  </span>
                  <FontAwesomeIcon icon="sort" onClick={sort('id')} />
                </th>
                <th className="hand" onClick={sort('whonetAbxCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.whonetAbxCode">Whonet Abx Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('whoCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.whoCode">Who Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dinCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.dinCode">Din Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jacCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.jacCode">Jac Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eucastCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.eucastCode">Eucast Code</Translate>
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.userCode">User Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('antibiotic')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.antibiotic">Antibiotic</Translate>
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('guidelines')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.guidelines">Guidelines</Translate>
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('antiboticClass')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.antiboticClass">Antibotic Class</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clsi')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.clsi">Clsi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eucast')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.eucast">Eucast</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sfm')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.sfm">Sfm</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('srga')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.srga">Srga</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('bsac')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.bsac">Bsac</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('din')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.din">Din</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('neo')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.neo">Neo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('afa')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.afa">Afa</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('abxNumber')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.abxNumber">Abx Number</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('potency')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.potency">Potency</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('atcCode')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.atcCode">Atc Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('profClass')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.profClass">Prof Class</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ciaCategory')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.ciaCategory">Cia Category</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clsiOrder')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.clsiOrder">Clsi Order</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('eucastOrder')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.eucastOrder">Eucast Order</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('human')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.human">Human</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('veterinary')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.veterinary">Veterinary</Translate>
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('animalGp')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.animalGp">Animal Gp</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loinccomp')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loinccomp">Loinccomp</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincgen')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincgen">Loincgen</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincdisk')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincdisk">Loincdisk</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincmic')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincmic">Loincmic</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincetest')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincetest">Loincetest</Translate>
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincslow')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincslow">Loincslow</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincafb')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincafb">Loincafb</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincsbt')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincsbt">Loincsbt</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('loincmlc')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.loincmlc">Loincmlc</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateEntered')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.dateEntered">Date Entered</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateModified')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.dateModified">Date Modified</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comments')}>
                  <Translate contentKey="amrInterpreationApp.antibiotic.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
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
                      <Button
                        tag={Link}
                        to={`${match.url}/${antibiotic.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${antibiotic.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Antibiotic);
