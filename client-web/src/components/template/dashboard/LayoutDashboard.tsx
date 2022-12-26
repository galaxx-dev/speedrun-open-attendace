import { Content, Main, PageLayout } from '@atlaskit/page-layout'
import { ReactNode } from 'react'
import LeftSidebarDashboard from './left-sidebar-dashboard'
import TopNavigationDashboard from './top-navigation-dashboard'

interface Props {
  children: ReactNode
}

/**
 * @version v0.1.1
 */
export default function LayoutDashboard({ children }: Props) {
  const SlotInternal = () => (
    <div className="p-6 border bg-orange-200 w-full h-screen">{children}</div>
  )

  return (
    <PageLayout>
      <TopNavigationDashboard />

      <Content testId="content">
        <LeftSidebarDashboard />

        <Main id="main-content" skipLinkTitle="Main Content" isFixed>
          <div className="p-6 border bg-gray-700 text-gray-200 w-full">{children}</div>
          {/* <SlotInternal /> */}
        </Main>
      </Content>
    </PageLayout>
  )
}
