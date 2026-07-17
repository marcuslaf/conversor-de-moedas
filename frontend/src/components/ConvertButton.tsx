'use client';

interface ConvertButtonProps {
  onClick: () => void;
  isLoading: boolean;
  disabled?: boolean;
}

export default function ConvertButton({ onClick, isLoading, disabled }: ConvertButtonProps) {
  return (
    <button
      onClick={onClick}
      disabled={isLoading || disabled}
      className="w-full py-4 px-6 bg-primary-600 hover:bg-primary-700 
                 text-white font-semibold rounded-xl
                 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:ring-offset-2
                 transition-all duration-200
                 disabled:bg-gray-400 disabled:cursor-not-allowed
                 active:scale-[0.98]
                 shadow-lg shadow-primary-500/25 hover:shadow-xl hover:shadow-primary-500/30"
    >
      {isLoading ? (
        <span className="flex items-center justify-center gap-2">
          <svg
            className="animate-spin h-5 w-5 text-white"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
          >
            <circle
              className="opacity-25"
              cx="12"
              cy="12"
              r="10"
              stroke="currentColor"
              strokeWidth="4"
            />
            <path
              className="opacity-75"
              fill="currentColor"
              d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
            />
          </svg>
          Convertendo...
        </span>
      ) : (
        'Converter'
      )}
    </button>
  );
}
