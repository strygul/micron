import {addItem, removeItem, SelectBarState} from "./selectBarSlice";
import selectBarReducer from './selectBarSlice'
import {OptionType} from "@atlaskit/select";

describe('counter reducer', () => {
    let optionOne = { label: 'One', value: 'one' };
    let optionTwo = { label: 'Two', value: 'two' };
    let optionThree = {label: 'New', value: 'new'};

    const initialState: SelectBarState = {
      selected: [
          optionOne,
          optionTwo,
      ]
    };

    it("should handle initial state", () => {
        expect(selectBarReducer(undefined, {type: 'unknown'})).toEqual({
            selected: [],
        });
    });

    it('should handle addItem', () => {
        let newOption: OptionType = optionThree;
        const actual = selectBarReducer(
           initialState,
           addItem(newOption),
       );
       expect(actual.selected).toEqual([optionOne, optionTwo, optionThree])
    });

    it('should handle removeItem', () => {
        let optionToRemove: OptionType = optionOne
        const actual = selectBarReducer(
            initialState,
            removeItem(optionToRemove),
        );
        expect(actual.selected).toEqual([optionTwo])
    });
});