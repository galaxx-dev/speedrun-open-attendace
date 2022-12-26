import AddonIcon from '@atlaskit/icon/glyph/addon'
import { Header as HeaderAtlaskit } from '@atlaskit/side-navigation'

import Link from 'next/link'

/**
 * @version v0.1.1
 */
export default function Header() {
  return (
    <HeaderAtlaskit
      component={({ children, ...props }) => (
        <Link href="/dashboard" {...props}>
          {children}
        </Link>
      )}
      iconBefore={<AddonIcon label="" />}
      description="Next-gen attendance information system"
    >
      Open Attendance
    </HeaderAtlaskit>
  )
}
