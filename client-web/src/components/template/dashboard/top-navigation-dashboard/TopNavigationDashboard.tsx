import { AtlassianNavigation, PrimaryButton } from '@atlaskit/atlassian-navigation'
import { TopNavigation } from '@atlaskit/page-layout'

import DefaultCreate from './DefaultCreate'
import DefaultProductHome from './DefaultProductHome'
import DefaultProfile from './DefaultProfile'
import DefaultSearch from './DefaultSearch'

/**
 * @version v0.1.1
 */
export default function TopNavigationDashboard() {
  return (
    <TopNavigation isFixed={true} id="confluence-navigation" skipLinkTitle="Confluence Navigation">
      <AtlassianNavigation
        label="site"
        moreLabel="More"
        primaryItems={[
          <PrimaryButton isHighlighted key={0}>
            Item 1
          </PrimaryButton>,

          <PrimaryButton key={1}>Item 2</PrimaryButton>,
        ]}
        renderCreate={DefaultCreate}
        renderProductHome={DefaultProductHome}
        renderSearch={DefaultSearch}
        renderProfile={DefaultProfile}
      />
    </TopNavigation>
  )
}
