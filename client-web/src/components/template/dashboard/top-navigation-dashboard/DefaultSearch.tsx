import EditorSearchIcon from '@atlaskit/icon/glyph/editor/search'
import TextField from '@atlaskit/textfield'

import { useState } from 'react'

/**
 * @version v0.1.1
 */
export default function DefaultSearch() {
  const [value, setValue] = useState('')

  const onChange = (event: any) => {
    console.log('search clicked with value: ', event.target.value)
    setValue(event.target.value)
  }

  return (
    <TextField
      name="basic"
      aria-label="default text field"
      elemBeforeInput={<EditorSearchIcon primaryColor="gray" label="" />}
      isCompact
      placeholder="search..."
      onChange={onChange}
    />
  )
}
