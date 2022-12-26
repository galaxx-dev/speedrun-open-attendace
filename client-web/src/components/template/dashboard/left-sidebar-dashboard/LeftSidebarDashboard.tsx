import { LeftSidebar } from '@atlaskit/page-layout'
import {
  NavigationFooter,
  NavigationHeader,
  NestableNavigationContent,
  SideNavigation,
} from '@atlaskit/side-navigation'

import Content from './Content'
import Footer from './Footer'
import Header from './Header'

/**
 * @version v0.1.1
 */
export default function LeftSidebarDashboard() {
  return (
    <LeftSidebar
      isFixed
      width={300}
      id="project-navigation"
      skipLinkTitle="Project Navigation"
      testId="left-sidebar"
    >
      <SideNavigation label="project" testId="side-navigation">
        <NavigationHeader>
          <Header />
        </NavigationHeader>

        <NestableNavigationContent initialStack={[]} testId="nestable-navigation-content">
          <Content />
        </NestableNavigationContent>

        <NavigationFooter>
          <Footer />
        </NavigationFooter>
      </SideNavigation>
    </LeftSidebar>
  )
}
