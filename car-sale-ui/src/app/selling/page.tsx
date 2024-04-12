import { Button } from "@/components/ui/button";
// import { Input } from "@/components/ui/input";
import { CardContent, Card } from "@/components/ui/card";
import { JSX, SVGProps } from "react";
// import { label } from "@/components/ui/label";
// import { Input } from "@/components/ui/input";
// import { SelectValue, SelectTrigger, Select } from "@/components/ui/select";
import { CarHeader } from "@/components/header/car-header";

export default function SellTransportation() {
  const carBrands = [
    "Lada (ВАЗ)",
    "Audi",
    "BMW",
    "Hyundai",
    "Kia",
    "Mercedes-Benz",
    "Nissan",
    "Renault",
    "Toyota",
    "Skoda",
    "Volkswagen",
  ];

  return (
    <>
      <CarHeader />
      <div className="max-w-4xl mx-auto py-10" style={{ width: 600 }}>
        <h1 className="text-4xl font-bold text-center mb-2">
          Продайте свой автомобиль
        </h1>
        <p className="text-center mb-8">
          Объявление смогут увидеть 3 000 000 человек ежедневно
        </p>
        <div className="flex justify-between mb-6">
          <Button
            className="bg-[#e6e6e6] text-white bg-black"
            variant="outline"
          >
            Легковые
          </Button>
          <Button className="bg-[#e6e6e6] text-black" variant="outline">
            Комтранс
          </Button>
          <Button className="bg-[#e6e6e6] text-black" variant="outline">
            Мото
          </Button>
        </div>
        <div className="mb-8">
          <input placeholder="Марка" />
        </div>
        <div className="grid grid-cols-3 gap-4">
          {carBrands.map((brand, index) => (
            <Card key={index} className="w-full bg-[#f2f2f2]">
              <CardContent>
                <CarIcon className="h-12 w-12 mx-auto mb-2" />
                <p className="text-center">{brand}</p>
              </CardContent>
            </Card>
          ))}
          <Card className="w-full bg-[#f2f2f2]">
            <CardContent className="flex items-center justify-center">
              <p className="text-center">Все марки</p>
            </CardContent>
          </Card>
        </div>
      </div>
    </>
  );
}

function CarIcon(props: JSX.IntrinsicAttributes & SVGProps<SVGSVGElement>) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-2.3c-.5-.4-1.1-.7-1.8-.7H5c-.6 0-1.1.4-1.4.9l-1.4 2.9A3.7 3.7 0 0 0 2 12v4c0 .6.4 1 1 1h2" />
      <circle cx="7" cy="17" r="2" />
      <path d="M9 17h6" />
      <circle cx="17" cy="17" r="2" />
    </svg>
  );
}
