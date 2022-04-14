import React, { useEffect, useState } from 'react';
import { Button, Label, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AvField, AvForm, AvGroup, AvCheckboxGroup, AvCheckbox, AvInput } from 'availity-reactstrap-validation';
import Select from 'react-select';
import { empty } from 'app/shared/util/entity-utils';
import CreatableSelect from 'react-select/creatable';

export interface ITargetPopup {
  title?: any;
  show: boolean;
  handleOk: any;
  handleCancel: any;
}

export const TargetUpdate = (props: ITargetPopup) => {
  const { show, title, handleOk, handleCancel } = props;
  const [data, setData] = useState({});
  const fileUpload = evt => {
    setData({ ...data, file: evt });
  };
  const setEmail = evt => {
    setData({ ...data, email: evt.target.value });
  };

  const saveEntity = () => {
    handleOk(data);
    setData({});
  };
  const cancel = () => {
    setData({});
    handleCancel();
  };
  return (
    <Modal isOpen={show} toggle={cancel}>
      <AvForm onSubmit={saveEntity}>
        <ModalHeader toggle={cancel}>{title}</ModalHeader>
        <ModalBody id="tooltip-custom-body">
          <AvInput name="email" placeholder={'email'} onChange={setEmail} />
          <label htmlFor="file">Choose a file</label>
          <AvInput name="file" className="inputfile" type="file" id="file" onChange={fileUpload} style={{ float: 'right' }} />
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={cancel}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-payment" data-cy="entityConfirmDeleteButton" type="submit" color="danger">
            <FontAwesomeIcon icon="trash" />
            &nbsp; OK
          </Button>
        </ModalFooter>
      </AvForm>
    </Modal>
  );
};
