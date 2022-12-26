import { Create } from '@atlaskit/atlassian-navigation'

/**
 * @version v0.1.1
 */
export default function DefaultCreate() {
  const handleClick = () => {
    console.log('asd')
  }

  return (
    <Create buttonTooltip="Create" iconButtonTooltip="Create" onClick={handleClick} text="Create" />
  )
}
