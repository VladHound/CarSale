import { Button } from "@/components/ui/button";
import { CarCatalog } from "./header/car-catalog";

export function HomeCarSalePage() {
  return (
    <div className="bg-white">
      <CarCatalog />
      <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <h2 className="text-2xl font-bold mt-6 mb-4">
          Легковые автомобили в Саратове
        </h2>
        <CarGrid />
        <Button className="bg-[#e20613] text-white mb-6">
          <a href="/car">Показать {countAllCar} предложений</a>
        </Button>
      </div>
    </div>
  );
}

const carBrands = [
  { name: "Lada (ВАЗ)", counts: 100 },
  { name: "Audi", counts: 102 },
  { name: "BMW", counts: 166 },
  { name: "Changan", counts: 66 },
  { name: "Chery", counts: 53 },
  { name: "Chevrolet", counts: 130 },
  { name: "Daewoo", counts: 69 },
  { name: "EXEED", counts: 70 },
  { name: "FAW", counts: 63 },
  { name: "Ford", counts: 124 },
  { name: "Geely", counts: 55 },
  { name: "Haval", counts: 21 },
  { name: "Honda", counts: 44 },
  { name: "Hyundai", counts: 272 },
  { name: "Jetta", counts: 52 },
  { name: "Kia", counts: 264 },
  { name: "Land Rover", counts: 27 },
  { name: "Lexus", counts: 58 },
  { name: "Livan", counts: 353 },
  { name: "Mazda", counts: 83 },
  { name: "Mercedes-Benz", counts: 130 },
  { name: "Mitsubishi", counts: 78 },
  { name: "Nissan", counts: 164 },
  { name: "OMODA", counts: 23 },
  { name: "Opel", counts: 97 },
  { name: "Peugeot", counts: 25 },
  { name: "Renault", counts: 181 },
  { name: "Skoda", counts: 90 },
];

let countAllCar = carBrands.reduce((total, car) => total + car.counts, 0);

function CarGrid() {
  return (
    <div className="grid grid-cols-4 gap-4 mb-6">
      {carBrands.map((car) => (
        <div key={car.name} className="flex flex-col text-white">
          <Button key={car.name} className="mb-1 bg-[#4C566A]">
            {car.name} ({car.counts})
          </Button>
        </div>
      ))}
    </div>
  );
}
