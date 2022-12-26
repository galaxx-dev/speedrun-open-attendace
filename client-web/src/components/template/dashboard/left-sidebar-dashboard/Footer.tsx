import StarIcon from '@atlaskit/icon/glyph/star'
import { Footer as FooterAtlaskit } from '@atlaskit/side-navigation'

import Link from 'next/link'

/**
 * @version v0.1.1
 */
export default function Footer() {
  return (
    <FooterAtlaskit
      iconBefore={<StarIcon label="" />}
      description={
        <div>
          <Link href="#">Give feedback</Link> {' ∙ '}
          <Link href="#">Learn more</Link>
        </div>
      }
    >
      You&lsquo;re in a next gen-project
    </FooterAtlaskit>
  )
}
