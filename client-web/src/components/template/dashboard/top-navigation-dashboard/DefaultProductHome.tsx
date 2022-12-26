import { ProductHome } from '@atlaskit/atlassian-navigation'
import { ConfluenceIcon, ConfluenceLogo } from '@atlaskit/logo'

/**
 * @version v0.1.1
 */
export default function DefaultProductHome() {
  return (
    <ProductHome
      onClick={console.log}
      icon={ConfluenceIcon}
      logo={ConfluenceLogo}
      siteTitle="Open Attendance"
    />
  )
}
