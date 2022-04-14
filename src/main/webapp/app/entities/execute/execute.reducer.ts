import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, empty } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExecute, defaultValue } from 'app/shared/model/execute.model';

export const ACTION_TYPES = {
  FETCH_EXECUTE_LIST: 'execute/FETCH_EXECUTE_LIST',
  FETCH_EXECUTE: 'execute/FETCH_EXECUTE',
  CREATE_EXECUTE: 'execute/CREATE_EXECUTE',
  INTERPRETATION_EXECUTE: 'execute/INTERPRETATION_EXECUTE',
  INTERPRETATION_EXECUTE_FILE: 'execute/INTERPRETATION_EXECUTE_FILE',
  UPDATE_EXECUTE: 'execute/UPDATE_EXECUTE',
  PARTIAL_UPDATE_EXECUTE: 'execute/PARTIAL_UPDATE_EXECUTE',
  DELETE_EXECUTE: 'execute/DELETE_EXECUTE',
  RESET: 'execute/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExecute>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  result: null,
  fileDownload: null,
  fileDownloadName: '',
};

export type ExecuteState = Readonly<typeof initialState>;

// Reducer

export default (state: ExecuteState = initialState, action): ExecuteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXECUTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXECUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.INTERPRETATION_EXECUTE):
    case REQUEST(ACTION_TYPES.INTERPRETATION_EXECUTE_FILE):
      return {
        ...state,
        loading: true,
        fileDownload: null,
      };
    case REQUEST(ACTION_TYPES.CREATE_EXECUTE):
    case REQUEST(ACTION_TYPES.UPDATE_EXECUTE):
    case REQUEST(ACTION_TYPES.DELETE_EXECUTE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EXECUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.INTERPRETATION_EXECUTE):
      return {
        ...state,
        loading: false,
        result: null,
      };
    case FAILURE(ACTION_TYPES.FETCH_EXECUTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXECUTE):
    case FAILURE(ACTION_TYPES.CREATE_EXECUTE):
    case FAILURE(ACTION_TYPES.UPDATE_EXECUTE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EXECUTE):
    case FAILURE(ACTION_TYPES.DELETE_EXECUTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXECUTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXECUTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.INTERPRETATION_EXECUTE):
      return {
        ...state,
        loading: false,
        result: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.INTERPRETATION_EXECUTE_FILE):
      return {
        ...state,
        loading: false,
        fileDownload: action.payload.data,
        fileDownloadName: empty(action.payload.headers['x-filename']) ? 'output' : action.payload.headers['x-filename'],
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXECUTE):
    case SUCCESS(ACTION_TYPES.UPDATE_EXECUTE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EXECUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXECUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/executes';

// Actions

export const getEntities: ICrudGetAllAction<IExecute> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXECUTE_LIST,
    payload: axios.get<IExecute>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IExecute> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXECUTE,
    payload: axios.get<IExecute>(requestUrl),
  };
};

export const interpretationEntity: ICrudPutAction<any> = entity => async dispatch => {
  return await dispatch({
    type: ACTION_TYPES.INTERPRETATION_EXECUTE,
    payload: axios.post('api/whonet/interpretation-bulk', entity),
  });
};

export const interpretationFile: ICrudPutAction<any> = entity => async dispatch => {
  if (empty(entity.target.files) || entity.target.files.length !== 1) {
    return;
  }
  const formData = new FormData();
  const file = entity.target.files[0];
  formData.append('file', file, file.name);
  return await dispatch({
    type: ACTION_TYPES.INTERPRETATION_EXECUTE_FILE,
    payload: axios.post('api/whonet/interpretation-file', formData, { responseType: 'blob' }),
  });
};

export const createEntity: ICrudPutAction<IExecute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXECUTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExecute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXECUTE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IExecute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EXECUTE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExecute> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXECUTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
