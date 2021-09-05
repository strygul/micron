import {OptionType} from "@atlaskit/select";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../../../app/store";

export interface SelectBarState {
    selected: Array<OptionType>;
}

const initialState: SelectBarState = {
    selected: []
}

export const selectBarSlice = createSlice({
    name: 'selectBar',
    initialState,
    reducers: {
        addItem: (state, action: PayloadAction<OptionType>) => {
            state.selected.push(action.payload)
        },
        removeItem: (state, action: PayloadAction<OptionType>) => {
            state.selected = state.selected.filter(opt => {
                return JSON.stringify(opt) !== JSON.stringify(action.payload)
            })
        }
    }
})

export const { addItem, removeItem } = selectBarSlice.actions;

export const selectSelected = (state: RootState) => state.selectBar.selected

export default selectBarSlice.reducer
