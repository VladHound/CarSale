import Link from "next/link";
import { Button } from "@/components/ui/button";
import { count } from "console";

export function CarCatalog() {
  const carTypes = [
    "Легковые",
    "Коммерческие",
    "Электро",
    "Китайские",
    "Мото",
    "Внедорожники",
  ];

  const selectedTypeIndex = 0;

  return (
    <div
      className="flex justify-between items-center mt-5"
      style={{ marginLeft: 30 }}
    >
      <div className="flex space-x-4">
        {carTypes.map((type, index) => (
          <Button
            key={index}
            className={`${
              index === selectedTypeIndex ? "bg-black text-white" : "text-black"
            }`}
          >
            {type}
          </Button>
        ))}
      </div>
    </div>
  );
}
