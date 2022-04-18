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
  const [data, setData] = useState({ file: null, action: '' });
  const fileUpload = evt => {
    setData({ ...data, file: evt });
  };

  const actionHandle = evt => {
    setData({ ...data, action: evt.target.value === 'false' ? 'unpivot' : '' });
  };

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      handleOk({ ...values, action: values.action ? 'unpivot' : '', file: data.file });
      setData({ file: null, action: '' });
    }
  };

  const cancel = () => {
    setData({ file: null, action: '' });
    handleCancel();
  };

  return (
    <Modal isOpen={show} toggle={cancel}>
      <AvForm model={{ action: '', email: 'hkien02@gmail.com' }} onSubmit={saveEntity}>
        <ModalHeader toggle={cancel}>{title}</ModalHeader>
        <ModalBody id="tooltip-custom-body">
          <AvGroup>
            <label htmlFor="file">Choose a file to upload</label>
            <AvInput
              name="file"
              multiple
              required
              className="inputfile"
              type="file"
              id="file"
              onChange={fileUpload}
              style={{ float: 'right' }}
            />
          </AvGroup>
          <AvGroup>
            <label htmlFor="action">Unpivot result</label>
            <AvInput style={{ marginLeft: 10 }} type="checkbox" id="action" name="action" onChange={actionHandle} />
          </AvGroup>
          {data.action === 'unpivot' && (
            <AvGroup>
              <label htmlFor="breakpoint">Add break point</label>
              <AvInput style={{ marginLeft: 10 }} type="checkbox" id="breakpoint" name="breakpoint" />{' '}
            </AvGroup>
          )}
          {data.action === 'unpivot' && (
            <AvGroup>
              <label htmlFor="intrinsic">Add Intrinsic resistance</label>
              <AvInput style={{ marginLeft: 10 }} type="checkbox" id="intrinsic" name="intrinsic" />
            </AvGroup>
          )}
          {data.action === 'unpivot' && (
            <AvGroup>
              <label htmlFor="empty">Filter empty</label>
              <AvInput style={{ marginLeft: 10 }} type="checkbox" id="empty" name="empty" />
            </AvGroup>
          )}
          {data.action === 'unpivot' && (
            <AvGroup>
              <label htmlFor="equal">Filter equal</label>
              <AvInput style={{ marginLeft: 10 }} type="checkbox" id="equal" name="equal" />
            </AvGroup>
          )}
          <AvGroup>
            <label htmlFor="email">Email</label>
            <AvInput name="email" required type={'email'} placeholder={'Email for results'} />
          </AvGroup>
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
