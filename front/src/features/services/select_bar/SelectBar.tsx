import React from 'react';
import Select, {OptionType} from '@atlaskit/select';
// import { cities } from '../common/data';

export function SelectMultiExample() {


    const cities:  ReadonlyArray<OptionType> = [
        { label: 'Porto', value: 'porto' },
        { label: 'Lissabon', value: 'lissabon' },
    ]

    return(
        <div>
            <label htmlFor="multi-select-example">What cities have you lived in?</label>
            <Select
                inputId="multi-select-example"
                className="multi-select"
                classNamePrefix="react-select"
                options={cities}
                isMulti
                isSearchable={false}
                placeholder="Choose a city"
            />
        </div>
    )
}
