import DropboxIcon from '@atlaskit/icon/glyph/dropbox'
import FilterIcon from '@atlaskit/icon/glyph/filter'
import FolderIcon from '@atlaskit/icon/glyph/folder'
import LightbulbIcon from '@atlaskit/icon/glyph/lightbulb'
import QueuesIcon from '@atlaskit/icon/glyph/queues'
import SettingsIcon from '@atlaskit/icon/glyph/settings'
import WorldIcon from '@atlaskit/icon/glyph/world'

import HomeIcon from '@atlaskit/icon/glyph/home'
import { ButtonItem, LinkItem, NestingItem, Section } from '@atlaskit/side-navigation'

import Link from 'next/link'

/**
 * @version v0.1.1
 */
export default function Content() {
  return (
    <Section>
      <Link href="/dashboard" legacyBehavior passHref>
        <LinkItem iconBefore={<HomeIcon label="" />}>Dashboard</LinkItem>
      </Link>

      <NestingItem
        id="2"
        testId="filter-nesting-item"
        title="Filters"
        iconBefore={<FilterIcon label="" />}
        iconAfter={<LightbulbIcon label="" />}
      >
        <Section>
          <ButtonItem>Search issues</ButtonItem>
        </Section>
        <Section title="Starred">
          <ButtonItem>Everything me</ButtonItem>
          <ButtonItem>My open issues</ButtonItem>
          <ButtonItem>Reported by me</ButtonItem>
        </Section>
        <Section hasSeparator title="Other">
          <ButtonItem>All issues</ButtonItem>
          <ButtonItem>Open issues</ButtonItem>
          <ButtonItem>Created recently</ButtonItem>
          <ButtonItem>Resolved recently</ButtonItem>
        </Section>
        <Section hasSeparator>
          <ButtonItem>View all filters</ButtonItem>
        </Section>
      </NestingItem>

      <NestingItem id="1" isSelected title="Queues view" iconBefore={<QueuesIcon label="" />}>
        <Section title="Queues">
          <ButtonItem>Untriaged</ButtonItem>
          <ButtonItem>My feature work</ButtonItem>
          <ButtonItem>My bugfix work</ButtonItem>
          <ButtonItem>Signals</ButtonItem>
          <ButtonItem>Assigned to me</ButtonItem>
        </Section>
        <Section hasSeparator>
          <ButtonItem>New queue</ButtonItem>
        </Section>
      </NestingItem>

      <NestingItem
        id="3"
        iconBefore={<SettingsIcon label="" />}
        title="Settings"
        testId="settings-nesting-item"
      >
        <Section>
          <NestingItem iconBefore={<WorldIcon label="" />} id="3-1" title="Language settings">
            <Section>
              <ButtonItem>Customize</ButtonItem>
              <NestingItem id="3-1-1" title="German Settings">
                <Section>
                  <ButtonItem>Hallo Welt!</ButtonItem>
                </Section>
              </NestingItem>
              <NestingItem id="3-1-2" title="English Settings">
                <Section>
                  <ButtonItem>Hello World!</ButtonItem>
                </Section>
              </NestingItem>
            </Section>
          </NestingItem>
        </Section>
      </NestingItem>

      <NestingItem
        id="4"
        iconBefore={<DropboxIcon label="" />}
        title="Dropbox"
        testId="dropbox-nesting-item"
        isDisabled
      >
        <span />
      </NestingItem>

      <ButtonItem iconBefore={<FolderIcon label="" />}>Your work</ButtonItem>
    </Section>
  )
}
