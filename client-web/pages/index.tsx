import Head from 'next/head'
import Link from 'next/link'

export default function Home() {
  return (
    <>
      <Head>
        <title>Open Attendance</title>
        <meta
          name="description"
          content="Next-gen attendance information system. Open Attendance."
        />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <h1 className="text-3xl bg-indigo-300 font-bold underline">Open Attendance!</h1>

      <h3>
        Go to Login page: <Link href="/auth/login">Fly me!</Link>
      </h3>

      <h3>
        Go to Dashboard: <Link href="/dashboard">Fly me!</Link>
      </h3>
    </>
  )
}
