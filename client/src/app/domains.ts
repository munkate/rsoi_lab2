export interface Ship {

  uid: number;
  sh_title: string;
  skipper: string;
  year: number;
  capacity: number;
  type: string;

}

export class Ship {

  uid: number;
  sh_title: string;
  skipper: string;
  year: number;
  capacity: number;
  type: string;

}

export interface Shipment {

  uid: number;
  title: string;
  declare_value: number;
  unit_id: string;
  del_id: number;

}

export class Shipment {

  uid: number;
  title: string;
  declare_value: number;
  unit_id: string;
  del_id: number;

}
