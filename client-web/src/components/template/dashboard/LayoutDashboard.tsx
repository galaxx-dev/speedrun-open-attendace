import { Content, Main, PageLayout } from '@atlaskit/page-layout'
import { ReactNode } from 'react'
import { HeadWrapped } from '../HeadWrapped'
import LeftSidebarDashboard from './left-sidebar-dashboard'
import TopNavigationDashboard from './top-navigation-dashboard'

interface Props {
  children: ReactNode
  siteTitle: string
}

/**
 * @version v0.1.1
 */
export default function LayoutDashboard({ children, siteTitle }: Props) {
  const SlotInternal = () => (
    <div className="p-6 border bg-orange-200 w-full h-screen">{children}</div>
  )

  return (
    <>
      <HeadWrapped siteTitle={siteTitle} />

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
    </>
  )
}
