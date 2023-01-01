import { JiraIcon } from '@atlaskit/logo'
import { ButtonItem, MenuGroup, PopupMenuGroup, Section } from '@atlaskit/menu'
import { LayoutDashboard } from '~components/template/dashboard'

import { N800 } from '@atlaskit/theme/colors'
import { token } from '@atlaskit/tokens'

interface Props {}

/**
 * @version v0.1.1
 */
export default function IndexDashboardPage(props: Props) {
  return (
    <LayoutDashboard siteTitle="main dashboard">
      <div>
        <span>qwdqwd</span>
        <span>brtjnoi</span>
      </div>

      {/* <div className="p-6 border bg-gray-700 text-gray-200 w-full h-screen"> */}
      <div>
        <span>qwdqwd</span>
        <span>brtjnoi</span>
      </div>

      <div
        style={{
          display: 'inline-block',
          color: token('color.text', N800),
          backgroundColor: token('elevation.surface.overlay', '#fff'),
          boxShadow: token(
            'elevation.shadow.overlay',
            '0px 4px 8px rgba(9, 30, 66, 0.25), 0px 0px 1px rgba(9, 30, 66, 0.31)',
          ),
          borderRadius: 4,
          margin: '16px auto',
          minWidth: '320px',
          maxWidth: '100%',
        }}
      >
        <PopupMenuGroup>
          <Section>
            <ButtonItem iconBefore={<JiraIcon />} description={'dqwqwdqwdqwd'}>
              Spacecraft
            </ButtonItem>
          </Section>
        </PopupMenuGroup>
      </div>

      <div
        style={{
          color: token('color.text', N800),
          backgroundColor: token('elevation.surface.overlay', '#fff'),
          boxShadow: token(
            'elevation.shadow.overlay',
            '0px 4px 8px rgba(9, 30, 66, 0.25), 0px 0px 1px rgba(9, 30, 66, 0.31)',
          ),
          borderRadius: 4,
          maxWidth: 320,
          margin: '16px auto',
        }}
      >
        <MenuGroup>
          <Section title="Starred">
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen software project">
              Navigation System
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen service desk">
              Analytics Platform
            </ButtonItem>
          </Section>
          <Section title="Recent">
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen software project">
              Fabric Editor
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Classic business project">
              Content Services
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen software project">
              Trinity Mobile
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Classic service desk">
              Customer Feedback
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Classic software project">
              Design System
            </ButtonItem>
          </Section>
          <Section hasSeparator>
            <ButtonItem>View all projects</ButtonItem>
            <ButtonItem>Create project</ButtonItem>
          </Section>
        </MenuGroup>
      </div>

      <div
        style={{
          color: token('color.text', N800),
          backgroundColor: token('elevation.surface.overlay', '#fff'),
          boxShadow: token(
            'elevation.shadow.overlay',
            '0px 4px 8px rgba(9, 30, 66, 0.25), 0px 0px 1px rgba(9, 30, 66, 0.31)',
          ),
          borderRadius: 4,
          maxWidth: 320,
          margin: '16px auto',
        }}
      >
        <MenuGroup>
          <Section title="Starred">
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen software project">
              Navigation System
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen service desk">
              Analytics Platform
            </ButtonItem>
          </Section>
          <Section title="Recent">
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen software project">
              Fabric Editor
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Classic business project">
              Content Services
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Next-gen software project">
              Trinity Mobile
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Classic service desk">
              Customer Feedback
            </ButtonItem>
            <ButtonItem iconBefore={<JiraIcon />} description="Classic software project">
              Design System
            </ButtonItem>
          </Section>
          <Section hasSeparator>
            <ButtonItem>View all projects</ButtonItem>
            <ButtonItem>Create project</ButtonItem>
          </Section>
        </MenuGroup>
      </div>
      {/* </div> */}
    </LayoutDashboard>
  )
}
