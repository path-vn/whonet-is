import React, { useEffect, useState } from 'react';
import { IPayload, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { ToolTips } from 'app/shared/util/filter-modal';
import { empty } from 'app/shared/util/entity-utils';

export interface IAsyncSelectProps {
  contentKey: string;
  name?: string;
  filter: any;
  sortHandle: any;
  handle?: any;
  filterHandle?: any;
}

export declare type ICrudGetAllActionWithFilter<T> = (
  page?: number,
  size?: number,
  sort?: string,
  filer?: any
) => IPayload<T> | ((dispatch: any) => IPayload<T>);

export const FilterTableHeader = (props: IAsyncSelectProps) => {
  const [tooltipSync, setTooltipSync] = useState(false);
  useEffect(() => {
    if (!empty(props.handle)) {
      props.handle(props.name);
    }
  }, []);
  return (
    <>
      <ToolTips
        show={tooltipSync}
        title={props.contentKey}
        options={empty(props.filter) ? [] : props.filter[props.name]}
        handleOk={values => {
          if (!empty(props.filterHandle)) {
            props.filterHandle(values);
          }
          setTooltipSync(false);
        }}
        handleCancel={() => {
          setTooltipSync(false);
        }}
      />
      <span onClick={() => setTooltipSync(true)}>
        <Translate contentKey={props.contentKey}>{props.name}</Translate>
      </span>
      <FontAwesomeIcon icon="sort" onClick={() => props.sortHandle()} />
    </>
  );
};
