import React, { useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Select from 'react-select';
import { empty } from 'app/shared/util/entity-utils';
import makeAnimated from 'react-select/animated';

export interface IToolTips {
  show: boolean;
  title?: any;
  selected?: any[];
  body?: any;
  options?: any[];
  handleOk?: any;
  handleCancel?: any;
}

export const ToolTips = (props: IToolTips) => {
  const { show, title, body, handleOk, handleCancel } = props;
  const animatedComponents = makeAnimated();
  const [selected, setSelected] = useState(
    empty(props.selected)
      ? []
      : props.selected.map(k => {
          return { value: k, label: k };
        })
  );
  const changeHandle = values => {
    setSelected(values);
  };

  return (
    <Modal isOpen={show} toggle={handleCancel}>
      <ModalHeader toggle={handleCancel}>
        Filter: <Translate contentKey={props.title} />
      </ModalHeader>

      <ModalBody id="tooltip-custom-body">
        <Select
          closeMenuOnSelect={false}
          components={animatedComponents}
          defaultValue={selected}
          isMulti
          onChange={values => {
            changeHandle(values);
          }}
          options={
            empty(props.options)
              ? []
              : props.options.map(k => {
                  return { value: k, label: k };
                })
          }
        />
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleCancel}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-payment" data-cy="entityConfirmDeleteButton" color="danger" onClick={() => handleOk(selected)}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; OK
        </Button>
      </ModalFooter>
    </Modal>
  );
};
