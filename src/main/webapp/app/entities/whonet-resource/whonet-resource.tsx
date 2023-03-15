import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './whonet-resource.reducer';
import { IWhonetResource } from 'app/shared/model/whonet-resource.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IWhonetResourceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const WhonetResource = (props: IWhonetResourceProps) => {
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

  const { whonetResourceList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="whonet-resource-heading" data-cy="WhonetResourceHeading">
        <Translate contentKey="amrInterpreationApp.whonetResource.home.title">Whonet Resources</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.whonetResource.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="amrInterpreationApp.whonetResource.home.createLabel">Create new Whonet Resource</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {whonetResourceList && whonetResourceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('uploadDate')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.uploadDate">Upload Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('antibiotic')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.antibiotic">Antibiotic</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('organism')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.organism">Organism</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('intrinsicResistance')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.intrinsicResistance">Intrinsic Resistance</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('expertRule')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.expertRule">Expert Rule</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('breakPoint')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.breakPoint">Break Point</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('importedDate')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.importedDate">Imported Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('message')}>
                  <Translate contentKey="amrInterpreationApp.whonetResource.message">Message</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {whonetResourceList.map((whonetResource, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${whonetResource.id}`} color="link" size="sm">
                      {whonetResource.id}
                    </Button>
                  </td>
                  <td>
                    {whonetResource.uploadDate ? (
                      <TextFormat type="date" value={whonetResource.uploadDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{whonetResource.antibiotic}</td>
                  <td>{whonetResource.organism}</td>
                  <td>{whonetResource.intrinsicResistance}</td>
                  <td>{whonetResource.expertRule}</td>
                  <td>{whonetResource.breakPoint}</td>
                  <td>{whonetResource.status}</td>
                  <td>
                    {whonetResource.importedDate ? (
                      <TextFormat type="date" value={whonetResource.importedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{whonetResource.message}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${whonetResource.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${whonetResource.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${whonetResource.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="amrInterpreationApp.whonetResource.home.notFound">No Whonet Resources found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={whonetResourceList && whonetResourceList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ whonetResource }: IRootState) => ({
  whonetResourceList: whonetResource.entities,
  loading: whonetResource.loading,
  totalItems: whonetResource.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WhonetResource);
