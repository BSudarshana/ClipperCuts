import {Gender} from "../../../entity/gender";

describe('CustomerComponent', () => {

  let gender:Gender;

  beforeEach(()=>{
    gender = new Gender(1,"Male");
  });

  it('should create instance correctly with values', () => {
    expect(gender).not.toBeNull();
  });

});
