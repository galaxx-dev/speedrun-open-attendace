import Head from 'next/head'
import characterFormatter from '~utils/character-formatter'

interface Props {
  siteTitle: string
}

export default function HeadWrapped({ siteTitle }: Props) {
  return (
    <Head>
      <title>{characterFormatter.capsFirstChar(siteTitle)} · Open Attendance</title>
    </Head>
  )
}
