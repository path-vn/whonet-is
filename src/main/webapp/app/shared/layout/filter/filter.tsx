import Popup from 'reactjs-popup';
import React, { useState } from 'react';
//

export interface IFilterProps {
  data?: any;
}

type IFilterState = { isOpen: boolean };

class FilterPopup extends React.Component<IFilterProps, IFilterState> {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
    };
  }

  setOpen(isOpen) {
    this.setState(isOpen);
  }

  open() {
    return this.state.isOpen;
  }

  closeModal() {
    this.setOpen(false);
  }

  render() {
    return (
      <div>
        <button type="button" className="button" onClick={() => this.setOpen(!this.open())}>
          Controlled Popup
        </button>
        <Popup open={this.open()} closeOnDocumentClick onClose={this.closeModal}>
          <div className="modal">
            <a className="close" onClick={this.closeModal}>
              &times;
            </a>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Beatae magni omnis delectus nemo, maxime molestiae dolorem numquam
            mollitia, voluptate ea, accusamus excepturi deleniti ratione sapiente! Laudantium, aperiam doloribus. Odit, aut.
          </div>
        </Popup>
      </div>
    );
  }
}

export default FilterPopup;
