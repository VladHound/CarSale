import { CarHeader } from "@/components/header/car-header";
import { HomeCarSalePage } from "@/components/home-car-sale-page";

import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/globals.css";

import Head from "next/head";

export default function Home() {
  return (
    <>
      <CarHeader />
      <HomeCarSalePage />
    </>
  );
}
