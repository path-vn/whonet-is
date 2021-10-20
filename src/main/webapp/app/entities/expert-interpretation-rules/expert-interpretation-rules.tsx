import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './expert-interpretation-rules.reducer';
import { IExpertInterpretationRules } from 'app/shared/model/expert-interpretation-rules.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IExpertInterpretationRulesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ExpertInterpretationRules = (props: IExpertInterpretationRulesProps) => {
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

  const { expertInterpretationRulesList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="expert-interpretation-rules-heading" data-cy="ExpertInterpretationRulesHeading">
        <Translate contentKey="amrInterpreationApp.expertInterpretationRules.home.title">Expert Interpretation Rules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="amrInterpreationApp.expertInterpretationRules.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="amrInterpreationApp.expertInterpretationRules.home.createLabel">
              Create new Expert Interpretation Rules
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {expertInterpretationRulesList && expertInterpretationRulesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ruleCode')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.ruleCode">Rule Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('organismCode')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.organismCode">Organism Code</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('organismCodeType')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.organismCodeType">Organism Code Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ruleCriteria')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.ruleCriteria">Rule Criteria</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('affectedAntibiotics')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.affectedAntibiotics">Affected Antibiotics</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('antibioticExceptions')}>
                  <Translate contentKey="amrInterpreationApp.expertInterpretationRules.antibioticExceptions">
                    Antibiotic Exceptions
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {expertInterpretationRulesList.map((expertInterpretationRules, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${expertInterpretationRules.id}`} color="link" size="sm">
                      {expertInterpretationRules.id}
                    </Button>
                  </td>
                  <td>{expertInterpretationRules.id}</td>
                  <td>{expertInterpretationRules.ruleCode}</td>
                  <td>{expertInterpretationRules.description}</td>
                  <td>{expertInterpretationRules.organismCode}</td>
                  <td>{expertInterpretationRules.organismCodeType}</td>
                  <td>{expertInterpretationRules.ruleCriteria}</td>
                  <td>{expertInterpretationRules.affectedAntibiotics}</td>
                  <td>{expertInterpretationRules.antibioticExceptions}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${expertInterpretationRules.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${expertInterpretationRules.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${expertInterpretationRules.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="amrInterpreationApp.expertInterpretationRules.home.notFound">
                No Expert Interpretation Rules found
              </Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={expertInterpretationRulesList && expertInterpretationRulesList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ expertInterpretationRules }: IRootState) => ({
  expertInterpretationRulesList: expertInterpretationRules.entities,
  loading: expertInterpretationRules.loading,
  totalItems: expertInterpretationRules.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExpertInterpretationRules);
